<?php

declare(strict_types=1);

function renderLogin(string $error = ''): void
{
?>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Вход — Админка</title>
    <style>
        *{box-sizing:border-box;font-family:system-ui,-apple-system,Segoe UI,Roboto,sans-serif}
        body{margin:0;background:#0f1026;color:#f4f4f9;display:flex;align-items:center;justify-content:center;height:100vh}
        .card{background:#1f2235;border-radius:12px;padding:32px;box-shadow:0 30px 60px rgba(15,16,38,.45);width:min(360px,90vw)}
        h1{margin:0 0 16px;font-size:22px;text-align:center}
        .error{background:rgba(255,107,107,.18);color:#ff6b6b;padding:10px 14px;border-radius:8px;margin-bottom:16px;text-align:center}
        form{display:flex;flex-direction:column;gap:12px}
        input{padding:10px 12px;border-radius:8px;border:1px solid rgba(244,244,249,.12);background:rgba(15,16,38,.8);color:#f4f4f9}
        button{padding:10px 12px;border-radius:8px;border:none;background:#7f9bff;color:#0f1026;font-weight:600;cursor:pointer}
    </style>
</head>
<body>
    <div class="card">
        <h1>Админка статей</h1>
        <?php if ($error !== ''): ?>
            <div class="error"><?= e($error) ?></div>
        <?php endif; ?>
        <form method="post">
            <input type="password" name="password" placeholder="Пароль" autofocus required>
            <button type="submit">Войти</button>
        </form>
    </div>
</body>
</html>
<?php
}

function renderAdminList(ArticleStore $store, array $config): void
{
    $articles = $store->all();
    $created = $_GET['created'] ?? null;
    $deleted = isset($_GET['deleted']);
?>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Статьи — Админка</title>
    <style>
        *{box-sizing:border-box;font-family:system-ui,-apple-system,Segoe UI,Roboto,sans-serif}
        body{margin:0;background:#0f1026;color:#f4f4f9;line-height:1.6}
        header{display:flex;align-items:center;justify-content:space-between;padding:18px 32px;background:#1f2235}
        header h1{margin:0;font-size:20px}
        nav{display:flex;gap:12px}
        a, button.link{color:#7f9bff;text-decoration:none}
        .container{max-width:1100px;margin:0 auto;padding:32px}
        .toolbar{display:flex;justify-content:space-between;align-items:center;margin-bottom:24px}
        .btn{display:inline-flex;align-items:center;gap:8px;padding:9px 16px;border-radius:999px;background:#7f9bff;color:#0f1026;font-weight:600;text-decoration:none}
        .alert{margin-bottom:18px;padding:10px 16px;border-radius:10px;background:rgba(127,155,255,.18);color:#7f9bff}
        table{width:100%;border-collapse:collapse;background:#1f2235;border-radius:12px;overflow:hidden;box-shadow:0 30px 60px rgba(15,16,38,.35)}
        th,td{padding:14px 18px;text-align:left;border-bottom:1px solid rgba(244,244,249,.06)}
        th{font-size:13px;text-transform:uppercase;letter-spacing:.08em;color:rgba(244,244,249,.6)}
        tr:last-child td{border-bottom:none}
        .actions{display:flex;gap:12px}
        .muted{color:rgba(244,244,249,.6)}
    </style>
</head>
<body>
    <header>
        <h1><?= e($config['site_name']) ?> — Статьи</h1>
        <nav>
            <a href="/api/articles" target="_blank">API</a>
            <a href="/admin/logout">Выйти</a>
        </nav>
    </header>
    <main class="container">
        <div class="toolbar">
            <div>
                <h2 style="margin:0">Список статей</h2>
                <p class="muted" style="margin:4px 0 0">Всего: <?= count($articles) ?></p>
            </div>
            <a class="btn" href="/admin/articles/create">+ Новая статья</a>
        </div>
        <?php if ($created): ?>
            <div class="alert">Статья «<?= e($created) ?>» создана</div>
        <?php endif; ?>
        <?php if ($deleted): ?>
            <div class="alert">Статья удалена</div>
        <?php endif; ?>
        <table>
            <thead>
                <tr>
                    <th>Заголовок</th>
                    <th>Slug</th>
                    <th>Опубликовано</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <?php if (empty($articles)): ?>
                    <tr>
                        <td colspan="4" class="muted">Пока нет статей. Нажмите «Новая статья» чтобы добавить первую.</td>
                    </tr>
                <?php else: ?>
                    <?php foreach ($articles as $article): ?>
                        <tr>
                            <td>
                                <div style="font-weight:600"><?= e($article['title']) ?></div>
                                <div class="muted" style="font-size:14px"><?= e($article['description']) ?></div>
                            </td>
                            <td class="muted"><?= e($article['slug']) ?></td>
                            <td class="muted"><?= e($article['publishedAt']) ?></td>
                            <td>
                                <div class="actions">
                                    <a href="/admin/articles/<?= e($article['slug']) ?>/edit">Редактировать</a>
                                    <form method="post" action="/admin/articles/<?= e($article['slug']) ?>/delete" onsubmit="return confirm('Удалить статью?');">
                                        <button class="link" style="background:none;border:none;padding:0;cursor:pointer">Удалить</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                <?php endif; ?>
            </tbody>
        </table>
    </main>
</body>
</html>
<?php
}

function renderArticleForm(ArticleStore $store, array $config, ?array $article, string $error = '', array $old = []): void
{
    $isEdit = $article !== null && isset($article['slug']);
    $values = [
        'title' => $old['title'] ?? ($article['title'] ?? ''),
        'description' => $old['description'] ?? ($article['description'] ?? ''),
        'content' => $old['content'] ?? ($article['content'] ?? ''),
        'image' => $old['image'] ?? ($article['image'] ?? ''),
        'publishedAt' => $old['publishedAt'] ?? ($article['publishedAt'] ?? nowIso()),
    ];
    $slug = $article['slug'] ?? '';
    $saved = isset($_GET['saved']);
?>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><?= $isEdit ? 'Редактировать статью' : 'Новая статья' ?> — Админка</title>
    <style>
        *{box-sizing:border-box;font-family:system-ui,-apple-system,Segoe UI,Roboto,sans-serif}
        body{margin:0;background:#0f1026;color:#f4f4f9;line-height:1.6}
        header{display:flex;align-items:center;justify-content:space-between;padding:18px 32px;background:#1f2235}
        header h1{margin:0;font-size:20px}
        nav{display:flex;gap:12px}
        a{color:#7f9bff;text-decoration:none}
        .container{max-width:900px;margin:0 auto;padding:32px}
        .back{display:inline-flex;margin-bottom:18px}
        .alert{margin-bottom:18px;padding:10px 16px;border-radius:10px;background:rgba(127,155,255,.18);color:#7f9bff}
        .error{background:rgba(255,107,107,.18);color:#ff6b6b}
        form{display:flex;flex-direction:column;gap:18px}
        label{display:flex;flex-direction:column;gap:8px;font-weight:600}
        input, textarea{padding:10px 12px;border-radius:10px;border:1px:1px solid rgba(244,244,249,.12);border:1px solid rgba(244,244,249,.12);background:rgba(15,16,38,.8);color:#f4f4f9;font-size:16px;font-family:inherit}
        textarea{min-height:320px;resize:vertical;line-height:1.8}
        .actions{display:flex;gap:12px}
        .btn{padding:10px 18px;border-radius:999px;border:none;background:#7f9bff;color:#0f1026;font-weight:600;cursor:pointer;text-decoration:none}
        .btn-ghost{background:transparent;color:#f4f4f9}
    </style>
</head>
<body>
    <header>
        <h1><?= e($config['site_name']) ?> — <?= $isEdit ? 'Редактирование' : 'Создание' ?></h1>
        <nav>
            <a href="/admin">К списку</a>
            <a href="/admin/logout">Выйти</a>
        </nav>
    </header>
    <main class="container">
        <a class="back" href="/admin">← Назад к списку</a>
        <?php if ($saved): ?>
            <div class="alert">Изменения сохранены</div>
        <?php endif; ?>
        <?php if ($error !== ''): ?>
            <div class="alert error"><?= e($error) ?></div>
        <?php endif; ?>
        <form method="post">
            <label>
                Заголовок
                <input name="title" value="<?= e($values['title']) ?>" required>
            </label>
            <label>
                Описание (короткое)
                <input name="description" value="<?= e($values['description']) ?>">
            </label>
            <label>
                Изображение (URL)
                <input name="image" value="<?= e($values['image']) ?>" placeholder="https://...">
            </label>
            <label>
                Дата публикации (ISO 8601)
                <input name="publishedAt" value="<?= e($values['publishedAt']) ?>">
            </label            >
            <label>
                Текст статьи (Markdown)
                <textarea name="content"><?= e($values['content']) ?></textarea>
            </label>
            <div class="actions">
                <button class="btn" type="submit">Сохранить</button                >
                <a class="btn btn-ghost" href="/admin">Отмена</a>
            </div>
        </form>
    </main>
</body>
</html>
<?php
}
