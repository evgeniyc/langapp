package com.example.langapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Category::class, Word::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope)) // Теперь все правильно
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() { // Убрали inner
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("AppDatabaseCallback", "onCreate called")
            INSTANCE?.let { database ->
                scope.launch {
                    val categoryDao = database.categoryDao()
                    val wordDao = database.wordDao()
                    val category1 = Category(name_ru = "Еда", name_de = "Essen")
                    val category2 = Category(name_ru = "Животные", name_de = "Tiere")
                    val category3 = Category(name_ru = "Цвета", name_de = "Farben")
                    val categoryId1: Int = categoryDao.insertCategory(category1).toInt()
                    val categoryId2: Int = categoryDao.insertCategory(category2).toInt()
                    val categoryId3: Int = categoryDao.insertCategory(category3).toInt()
                    wordDao.insertWord(Word(word_de = "Apfel", word_ru = "Яблоко", category_id = categoryId1))
                    wordDao.insertWord(Word(word_de = "Banane", word_ru = "Банан", category_id = categoryId1))
                    wordDao.insertWord(Word(word_de = "Hund", word_ru = "Собака", category_id = categoryId2))
                    wordDao.insertWord(Word(word_de = "Katze", word_ru = "Кошка", category_id = categoryId2))
                    wordDao.insertWord(Word(word_de = "Rot", word_ru = "Красный", category_id = categoryId3))
                    wordDao.insertWord(Word(word_de = "Blau", word_ru = "Синий", category_id = categoryId3))
                    Log.d("AppDatabaseCallback", "Categories and words inserted")
                }
            }
        }
    }
}