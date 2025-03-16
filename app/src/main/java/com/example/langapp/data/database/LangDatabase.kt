package com.example.langapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.langapp.data.entities.CategoryEntity
import com.example.langapp.data.entities.WordEntity
import com.example.langapp.database.CategoryDao
import com.example.langapp.database.WordDao

@Database(entities = [CategoryEntity::class, WordEntity::class], version = 4, exportSchema = false)
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
                    //.fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}