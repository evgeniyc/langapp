package com.example.langapp.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `category_time` (`categoryId` INTEGER NOT NULL, `time_spent_millis` INTEGER NOT NULL, PRIMARY KEY(`categoryId`))"
            )
        }
    }
}