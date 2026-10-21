<?php

declare(strict_types=1);

return [
    // Пароль админки. Меняется через переменную окружения ADMIN_PASSWORD.
    'admin_password' => getenv('ADMIN_PASSWORD') ?: 'capibara',

    // Куда складывать .md файлы со статьями.
    'content_dir' => __DIR__ . '/content/articles',

    // Публичный адрес сайта — используется в поле url у статьи.
    'site_url' => rtrim(getenv('SITE_URL') ?: 'http://localhost:8000', '/'),

    'site_name' => getenv('SITE_NAME') ?: 'Capibara',

    'site_country' => 'ru',
];
