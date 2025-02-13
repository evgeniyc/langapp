package com.example.langapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Category::class], version = 2, exportSchema = false) // Изменено здесь!
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    class AppDatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("AppDatabaseCallback", "onCreate called")
            INSTANCE?.let { database ->
                GlobalScope.launch {
                    val categoryDao = database.categoryDao()
                    categoryDao.insertCategory(Category(name_ru = "Еда", name_de = "Essen"))
                    categoryDao.insertCategory(Category(name_ru = "Животные", name_de = "Tiere"))
                    categoryDao.insertCategory(Category(name_ru = "Цвета", name_de = "Farben"))
                    Log.d("AppDatabaseCallback", "Categories inserted")
                }
            }
        }
    }
}