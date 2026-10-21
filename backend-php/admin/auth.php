<?php

declare(strict_types=1);

function adminAuth($config): void
{
    if (session_status() === PHP_SESSION_NONE) {
        session_start();
    }

    $uri = parse_url($_SERVER['REQUEST_URI'] ?? '/', PHP_URL_PATH) ?? '/';

    if ($uri === '/admin/login') {
        return;
    }

    if ($uri === '/docs' || $uri === '/swagger' || $uri === '/swagger/') {
        return;
    }

    if ($uri === '/openapi.json') {
        return;
    }

    if (strpos($uri, '/swagger/') === 0) {
        return;
    }

    if (!empty($_SESSION['admin_authenticated'])) {
        return;
    }

    redirect('/admin/login');
}

function isAdminAuthed(): bool
{
    if (session_status() === PHP_SESSION_NONE) {
        session_start();
    }

    return !empty($_SESSION['admin_authenticated']);
}

function requireAdmin(): void
{
    if (!isAdminAuthed()) {
        redirect('/admin/login');
    }
}

function login(string $password, string $adminPassword): bool
{
    if (session_status() === PHP_SESSION_NONE) {
        session_start();
    }

    if (hash_equals($adminPassword, $password)) {
        $_SESSION['admin_authenticated'] = true;
        $_SESSION['admin_login_time'] = time();
        return true;
    }

    return false;
}

function logout(): void
{
    if (session_status() === PHP_SESSION_NONE) {
        session_start();
    }

    $_SESSION = [];
    if (ini_get('session.use_cookies')) {
        $params = session_get_cookie_params();
        setcookie(session_name(), '', time() - 42000,
            $params['path'], $params['domain'],
            $params['secure'], $params['httponly']
        );
    }
    session_destroy();
}
