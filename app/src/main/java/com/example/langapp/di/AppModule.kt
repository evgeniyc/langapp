package com.example.langapp.di

import android.content.Context
import androidx.room.Room
import com.example.langapp.data.database.CategoryDao
import com.example.langapp.data.database.CategoryTimeDao
import com.example.langapp.data.database.LangDatabase
import com.example.langapp.data.database.WordDao
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.CategoryTimeRepository
import com.example.langapp.data.repositories.WordRepository
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
            "lang_database"
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

    @Provides
    fun provideCategoryTimeDao(appDatabase: LangDatabase): CategoryTimeDao {
        return appDatabase.categoryTimeDao()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepository(categoryDao)
    }

    @Provides
    @Singleton
    fun provideWordRepository(wordDao: WordDao): WordRepository {
        return WordRepository(wordDao)
    }

    @Provides
    @Singleton
    fun provideCategoryTimeRepository(categoryTimeDao: CategoryTimeDao): CategoryTimeRepository {
        return CategoryTimeRepository(categoryTimeDao)
    }
}