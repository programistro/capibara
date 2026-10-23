package com.example.capibara.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `mood` (
                `id` INTEGER NOT NULL,
                `score` INTEGER NOT NULL,
                `max` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE `owned_items` ADD COLUMN `selectedItem` INTEGER NOT NULL DEFAULT 0"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `news_articles` (
                `id` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `description` TEXT NOT NULL,
                `content` TEXT NOT NULL,
                `url` TEXT NOT NULL,
                `image` TEXT NOT NULL,
                `publishedAt` TEXT NOT NULL,
                `lang` TEXT NOT NULL,
                `sourceId` TEXT NOT NULL,
                `sourceName` TEXT NOT NULL,
                `sourceUrl` TEXT NOT NULL,
                `sourceCountry` TEXT NOT NULL,
                `sortIndex` INTEGER NOT NULL,
                `cachedAt` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )
    }
}