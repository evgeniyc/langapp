package com.example.langapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.langapp.data.entities.Category
import com.example.langapp.data.entities.Word
import com.example.langapp.data.dao.CategoryDao
import com.example.langapp.data.dao.WordDao

@Database(entities = [Category::class, Word::class], version = 3, exportSchema = false)
abstract class LangDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: LangDatabase? = null

        fun getDatabase(context: Context): LangDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LangDatabase::class.java,
                    "lang_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}