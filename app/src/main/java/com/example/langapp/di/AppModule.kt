package com.example.langapp.di

import android.content.Context
import androidx.room.Room
import com.example.langapp.data.database.LangDatabase
import com.example.langapp.data.database.CategoryDao
import com.example.langapp.data.database.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLangDatabase(@ApplicationContext context: Context): LangDatabase {
        return Room.databaseBuilder(
            context,
            LangDatabase::class.java,
            "lang_database" // Изменено имя базы данных
        ).build()
    }

    @Provides
    fun provideWordDao(appDatabase: LangDatabase): WordDao {
        return appDatabase.wordDao()
    }

    @Provides
    fun provideCategoryDao(appDatabase: LangDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }
}