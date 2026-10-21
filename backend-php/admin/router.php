<?php

declare(strict_types=1);

require_once __DIR__ . '/auth.php';
require_once __DIR__ . '/views.php';

$config = $GLOBALS['config'] ?? $config;
$store = $GLOBALS['store'] ?? $store;

adminAuth($config);

$uri = parse_url($_SERVER['REQUEST_URI'] ?? '/', PHP_URL_PATH) ?? '/';
$method = $_SERVER['REQUEST_METHOD'] ?? 'GET';

if ($uri === '/admin' && $method === 'GET') {
    requireAdmin();
    renderAdminList($store, $config);
    exit;
}

if ($uri === '/admin/login') {
    if (isAdminAuthed()) {
        redirect('/admin');
    }

    $error = '';

    if ($method === 'POST') {
        $password = (string) ($_POST['password'] ?? '');
        if (login($password, $config['admin_password'])) {
            redirect('/admin');
        } else {
            $error = 'Неверный пароль';
        }
    }

    renderLogin($error);
    exit;
}

if ($uri === '/admin/logout' && ($method === 'GET' || $method === 'POST')) {
    logout();
    redirect('/admin/login');
    exit;
}

if ($uri === '/admin/articles/create') {
    requireAdmin();

    if ($method === 'GET') {
        renderArticleForm($store, $config, null);
        exit;
    }

    if ($method === 'POST') {
        try {
            $article = $store->create([
                'title' => $_POST['title'] ?? '',
                'description' => $_POST['description'] ?? '',
                'content' => $_POST['content'] ?? '',
                'image' => $_POST['image'] ?? '',
                'publishedAt' => $_POST['publishedAt'] ?? nowIso(),
            ]);
            redirect('/admin?created=' . urlencode($article['slug']));
            exit;
        } catch (Throwable $e) {
            renderArticleForm($store, $config, null, 'Ошибка: ' . $e->getMessage(), $_POST);
            exit;
        }
    }
}

if (preg_match('#^/admin/articles/([a-zA-Z0-9\-]+)/edit$#', $uri, $m)) {
    requireAdmin();
    $slug = $m[1];

    if ($method === 'GET') {
        $article = $store->findBySlug($slug);
        if ($article === null) {
            redirect('/admin');
        }
        renderArticleForm($store, $config, $article);
        exit;
    }

    if ($method === 'POST') {
        try {
            $article = $store->update($slug, [
                'title' => $_POST['title'] ?? null,
                'description' => $_POST['description'] ?? null,
                'content' => $_POST['content'] ?? null,
                'image' => $_POST['image'] ?? null,
                'publishedAt' => $_POST['publishedAt'] ?? null,
            ]);

            if ($article === null) {
                redirect('/admin');
                exit;
            }

            redirect('/admin/articles/' . urlencode($article['slug']) . '/edit?saved=1');
            exit;
        } catch (Throwable $e) {
            $article = $store->findBySlug($slug) ?? $_POST;
            $article['slug'] = $slug;
            renderArticleForm($store, $config, $article, 'Ошибка: ' . $e->getMessage(), $_POST);
            exit;
        }
    }
}

if (preg_match('#^/admin/articles/([a-zA-Z0-9\-]+)/delete$#', $uri, $m) && $method === 'POST') {
    requireAdmin();
    $store->delete($m[1]);
    redirect('/admin?deleted=1');
    exit;
}

http_response_code(404);
echo 'Not found';
exit;
