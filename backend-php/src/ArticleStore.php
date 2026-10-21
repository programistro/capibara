<?php

declare(strict_types=1);

class ArticleStore
{
    private string $contentDir;

    public function __construct(string $contentDir)
    {
        $this->contentDir = $contentDir;

        if (!is_dir($this->contentDir)) {
            @mkdir($this->contentDir, 0755, true);
        }
    }

    public function all(): array
    {
        $articles = [];

        $files = glob($this->contentDir . '/*.md');
        if (!$files) {
            $files = [];
        }

        foreach ($files as $file) {
            $article = $this->parseFile($file);
            if ($article !== null) {
                $articles[] = $article;
            }
        }

        // Новые сверху по дате публикации
        usort($articles, function (array $a, array $b) {
            return strcmp($b['publishedAt'] ?? '', $a['publishedAt'] ?? '');
        });

        return $articles;
    }

    public function findBySlug(string $slug): ?array
    {
        $slug = $this->sanitizeSlug($slug);
        $file = $this->contentDir . '/' . $slug . '.md';

        if (!is_file($file)) {
            return null;
        }

        return $this->parseFile($file);
    }

    public function findById(string $id): ?array
    {
        $id = trim($id);
        foreach ($this->all() as $article) {
            if (($article['id'] ?? '') === $id) {
                return $article;
            }
        }

        return null;
    }

    public function create(array $input): array
    {
        $title = trim((string) ($input['title'] ?? ''));
        $description = trim((string) ($input['description'] ?? ''));
        $content = (string) ($input['content'] ?? '');
        $image = trim((string) ($input['image'] ?? ''));
        $publishedAt = trim((string) ($input['publishedAt'] ?? nowIso()));

        if ($title === '') {
            throw new InvalidArgumentException('Title required');
        }

        if ($publishedAt === '') {
            $publishedAt = nowIso();
        }

        $baseSlug = slugify($title);
        $slug = $baseSlug;
        $counter = 1;

        while (is_file($this->contentDir . '/' . $slug . '.md')) {
            $slug = $baseSlug . '-' . $counter++;
        }

        $id = $this->generateId($slug);

        $article = [
            'id' => $id,
            'slug' => $slug,
            'title' => $title,
            'description' => $description,
            'content' => $content,
            'image' => $image,
            'publishedAt' => $publishedAt,
            'lang' => 'ru',
        ];

        $this->writeFile($slug, $article);
        return $article;
    }

    public function update(string $slug, array $input): ?array
    {
        $slug = $this->sanitizeSlug($slug);
        $existing = $this->findBySlug($slug);

        if ($existing === null) {
            return null;
        }

        $title = array_key_exists('title', $input) ? trim((string) $input['title']) : ($existing['title'] ?? '');
        $description = array_key_exists('description', $input) ? trim((string) $input['description']) : ($existing['description'] ?? '');
        $content = array_key_exists('content', $input) ? (string) $input['content'] : ($existing['content'] ?? '');
        $image = array_key_exists('image', $input) ? trim((string) $input['image']) : ($existing['image'] ?? '');
        $publishedAt = array_key_exists('publishedAt', $input) ? trim((string) $input['publishedAt']) : ($existing['publishedAt'] ?? nowIso());

        if ($title === '') {
            throw new InvalidArgumentException('Title required');
        }

        if ($publishedAt === '') {
            $publishedAt = nowIso();
        }

        $newSlug = $slug;
        if (array_key_exists('title', $input)) {
            $candidate = slugify($title);
            if ($candidate !== '' && $candidate !== $slug) {
                $newSlug = $candidate;
                $counter = 1;
                while (is_file($this->contentDir . '/' . $newSlug . '.md') && $newSlug !== $slug) {
                    $newSlug = $candidate . '-' . $counter++;
                }
            }
        }

        $article = [
            'id' => $existing['id'] ?? $this->generateId($newSlug),
            'slug' => $newSlug,
            'title' => $title,
            'description' => $description,
            'content' => $content,
            'image' => $image,
            'publishedAt' => $publishedAt,
            'lang' => $existing['lang'] ?? 'ru',
        ];

        $this->writeFile($newSlug, $article);

        if ($newSlug !== $slug) {
            $oldFile = $this->contentDir . '/' . $slug . '.md';
            if (is_file($oldFile)) {
                @unlink($oldFile);
            }
        }

        return $article;
    }

    public function delete(string $slug): bool
    {
        $slug = $this->sanitizeSlug($slug);
        $file = $this->contentDir . '/' . $slug . '.md';

        if (!is_file($file)) {
            return false;
        }

        return @unlink($file);
    }

    private function writeFile(string $slug, array $article): void
    {
        $slug = $this->sanitizeSlug($slug);
        $file = $this->contentDir . '/' . $slug . '.md';

        $lines = [];
        $lines[] = '---';
        $lines[] = 'id: ' . $this->quote($article['id'] ?? $this->generateId($slug));
        $lines[] = 'slug: ' . $this->quote($slug);
        $lines[] = 'title: ' . $this->quote((string) ($article['title'] ?? ''));
        $lines[] = 'description: ' . $this->quote((string) ($article['description'] ?? ''));
        $lines[] = 'image: ' . $this->quote((string) ($article['image'] ?? ''));
        $lines[] = 'publishedAt: ' . $this->quote((string) ($article['publishedAt'] ?? nowIso()));
        $lines[] = 'lang: ' . $this->quote((string) ($article['lang'] ?? 'ru'));
        $lines[] = '---';
        $lines[] = '';
        $lines[] = (string) ($article['content'] ?? '');
        $lines[] = '';

        file_put_contents($file, implode("\n", $lines), LOCK_EX);
    }

    private function parseFile(string $file): ?array
    {
        $raw = file_get_contents($file);
        if ($raw === false) {
            return null;
        }

        if (strpos($raw, "---") !== 0) {
            return null;
        }

        $end = strpos($raw, "\n---", 3);
        if ($end === false) {
            $end = strpos($raw, "\r\n---", 3);
        }

        if ($end === false) {
            return null;
        }

        $frontmatter = substr($raw, 3, $end - 3);
        $body = substr($raw, $end);
        if (strpos($body, "\r\n") === 0) {
            $body = substr($body, 4);
        } elseif (strpos($body, "\n") === 0) {
            $body = substr($body, 2);
        }
        if (strpos($body, "---") === 0) {
            if (strpos($body, "\r\n") === 3) {
                $body = substr($body, 5);
            } elseif (strpos($body, "\n") === 3) {
                $body = substr($body, 4);
            }
        }

        $meta = [];
        foreach (preg_split('/\R/', trim($frontmatter)) as $line) {
            $line = trim($line);
            if ($line === '') {
                continue;
            }

            $pos = strpos($line, ':');
            if ($pos === false) {
                continue;
            }

            $key = trim(substr($line, 0, $pos));
            $value = trim(substr($line, $pos + 1));

            $value = $this->unquote($value);
            $meta[$key] = $value;
        }

        $slug = $meta['slug'] ?? basename($file, '.md');

        return [
            'id' => $meta['id'] ?? $this->generateId($slug),
            'slug' => $slug,
            'title' => $meta['title'] ?? '',
            'description' => $meta['description'] ?? '',
            'content' => trim($body),
            'image' => $meta['image'] ?? '',
            'publishedAt' => $meta['publishedAt'] ?? nowIso(),
            'lang' => $meta['lang'] ?? 'ru',
        ];
    }

    private function quote(string $value): string
    {
        $value = str_replace('"', '\"', $value);
        $value = str_replace("\n", ' ', $value);
        $value = str_replace("\r", ' ', $value);
        return '"' . $value . '"';
    }

    private function unquote(string $value): string
    {
        if (strlen($value) >= 2 && $value[0] === '"' && $value[strlen($value) - 1] === '"') {
            $value = substr($value, 1, -1);
            $value = str_replace('\"', '"', $value);
        }

        return $value;
    }

    private function sanitizeSlug(string $slug): string
    {
        $slug = preg_replace('/[^a-zA-Z0-9\-]+/', '', $slug) ?? '';
        return trim($slug, '-');
    }

    private function generateId(string $slug): string
    {
        return md5($slug . '|' . microtime(true) . '|' . random_int(100000, 999999));
    }
}
