package com.example.langapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.langapp.data.entities.Category
import com.example.langapp.data.entities.Word

@Database(entities = [Category::class, Word::class], version = 1, exportSchema = false)
abstract class LangDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var Instance: LangDatabase? = null

        fun getDatabase(context: Context): LangDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LangDatabase::class.java, "lang_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}