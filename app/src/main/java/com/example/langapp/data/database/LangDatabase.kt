package com.example.langapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.langapp.data.entities.CategoryEntity
import com.example.langapp.data.entities.CategoryTimeEntity
import com.example.langapp.data.entities.WordEntity
import android.util.Log

@Database(
    entities = [CategoryEntity::class, WordEntity::class, CategoryTimeEntity::class],
    version = 6,
    exportSchema = false
)
abstract class LangDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun wordDao(): WordDao
    abstract fun categoryTimeDao(): CategoryTimeDao
    companion object {
        private const val TAG = "LangDatabase"

        @Volatile
        private var INSTANCE: LangDatabase? = null

        fun getDatabase(context: Context): LangDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LangDatabase::class.java,
                    "lang_database"
                )
                    //.addCallback(sRoomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
        }
}
