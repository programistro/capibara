<?php

declare(strict_types=1);

require_once __DIR__ . '/src/helpers.php';
require_once __DIR__ . '/src/ArticleStore.php';

$config = require __DIR__ . '/config.php';

$store = new ArticleStore($config['content_dir']);

// Роутинг
$uri = parse_url($_SERVER['REQUEST_URI'] ?? '/', PHP_URL_PATH) ?? '/';
$method = $_SERVER['REQUEST_METHOD'] ?? 'GET';

// CORS
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

if ($method === 'OPTIONS') {
    http_response_code(204);
    exit;
}

// Swagger / OpenAPI - публичные
if ($uri === '/docs' || $uri === '/swagger' || $uri === '/swagger/') {
    header('Content-Type: text/html; charset=utf-8');
    readfile(__DIR__ . '/swagger/index.html');
    exit;
}

if ($uri === '/openapi.json') {
    header('Content-Type: application/json; charset=utf-8');
    readfile(__DIR__ . '/swagger/openapi.json');
    exit;
}

if (strpos($uri, '/swagger/') === 0) {
    $file = __DIR__ . $uri;
    if (is_file($file)) {
        $ext = strtolower(pathinfo($file, PATHINFO_EXTENSION));
        if ($ext === 'css') {
            header('Content-Type: text/css; charset=utf-8');
        } elseif ($ext === 'js') {
            header('Content-Type: application/javascript; charset=utf-8');
        } elseif ($ext === 'json') {
            header('Content-Type: application/json; charset=utf-8');
        } elseif ($ext === 'png') {
            header('Content-Type: image/png');
        } elseif ($ext === 'svg') {
            header('Content-Type: image/svg+xml');
        } else {
            header('Content-Type: text/plain; charset=utf-8');
        }
        readfile($file);
        exit;
    }
}

// Публичный API под формат Android-приложения
if ($uri === '/api/articles' && $method === 'GET') {
    $articles = $store->all();
    $result = [];

    foreach ($articles as $article) {
        $result[] = [
            'id' => $article['id'],
            'title' => $article['title'],
            'description' => $article['description'],
            'content' => $article['content'],
            'url' => $config['site_url'] . '/articles/' . $article['slug'],
            'image' => $article['image'],
            'publishedAt' => $article['publishedAt'],
            'lang' => $article['lang'] ?? 'ru',
            'source' => [
                'id' => 'capibara',
                'name' => $config['site_name'],
                'url' => $config['site_url'],
                'country' => $config['site_country'],
            ],
        ];
    }

    jsonResponse([
        'totalArticles' => count($result),
        'articles' => $result,
    ]);
    exit;
}

if (preg_match('#^/api/articles/([a-zA-Z0-9\-]+)/?$#', $uri, $m) && $method === 'GET') {
    $article = $store->findBySlug($m[1]);

    if ($article === null) {
        jsonResponse(['error' => 'Article not found'], 404);
        exit;
    }

    jsonResponse([
        'id' => $article['id'],
        'title' => $article['title'],
        'description' => $article['description'],
        'content' => $article['content'],
        'url' => $config['site_url'] . '/articles/' . $article['slug'],
        'image' => $article['image'],
        'publishedAt' => $article['publishedAt'],
        'lang' => $article['lang'] ?? 'ru',
        'source' => [
            'id' => 'capibara',
            'name' => $config['site_name'],
            'url' => $config['site_url'],
            'country' => $config['site_country'],
        ],
    ]);
    exit;
}

// Админка
require_once __DIR__ . '/admin/router.php';

http_response_code(404);
echo 'Not found';
exit;
