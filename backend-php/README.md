# Capibara Backend (PHP)

Максимально простой бэкенд для новостей-статей. Статьи хранятся в виде `.md` файлов с frontmatter. Есть админка для CRUD и публичный JSON API для Android-приложения.

## Быстрый старт (Docker)

```bash
cd backend-php
docker compose up --build -d
```

Сервис будет доступен на http://localhost:8000

## Переменные окружения

- `ADMIN_PASSWORD` — пароль для админки (по умолчанию `capibara`)
- `SITE_URL` — базовый URL (по умолчанию `http://localhost:8000`)
- `SITE_NAME` — название источника в API

## Админка

- Логин: http://localhost:8000/admin/login
- Список: http://localhost:8000/admin
- Пароль: `capibara` (или из `ADMIN_PASSWORD`)

В админке можно создавать, редактировать и удалять статьи. Статьи пишутся в формате Markdown.

## Публичный API (совместим с Android)

### Список статей
`GET /api/articles`

Формат ответа:

```json
{
  "totalArticles": 2,
  "articles": [
    {
      "id": "...",
      "title": "...",
      "description": "...",
      "content": "...",
      "url": "http://localhost:8000/articles/slug",
      "image": "...",
      "publishedAt": "2026-10-15T17:27:00Z",
      "lang": "ru",
      "source": {
        "id": "capibara",
        "name": "Capibara",
        "url": "http://localhost:8000",
        "country": "ru"
      }
    }
  ]
}
```

### Одна статья
`GET /api/articles/{slug}`

Возвращает объект статьи в том же формате.

## Формат хранения (.md)

Файлы лежат в `content/articles/`. Пример:

```markdown
---
id: "..."
slug: "vrach-raskryl-pravdu"
title: "Врач раскрыл правду..."
description: "Короткое описание"
image: "https://..."
publishedAt: "2026-10-15T17:27:00Z"
lang: "ru"
---
Текст статьи в формате Markdown.
```

## Локальный запуск без Docker (PHP 8.1+)

```bash
cd backend-php
mkdir -p content/articles sessions
SESSION_SAVE_PATH=./sessions php -S 0.0.0.0:8000
```
