FROM php:8.3-cli-alpine

WORKDIR /app

RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Europe/Moscow /etc/localtime \
    && echo "Europe/Moscow" > /etc/timezone

COPY . /app

RUN mkdir -p /app/content/articles /app/sessions \
    && chmod -R 777 /app/content /app/sessions

ENV SESSION_SAVE_PATH=/app/sessions

EXPOSE 8000

CMD ["php", "-S", "0.0.0.0:8000", "-t", "/app", "/app/index.php"]
