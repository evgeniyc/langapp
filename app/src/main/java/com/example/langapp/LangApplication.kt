package com.example.langapp

import android.app.Application
import android.util.Log
import com.example.langapp.data.AppContainer
import com.example.langapp.data.AppDataContainer
import com.example.langapp.data.database.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LangApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        Log.d("LangApplication", "onCreate called") // Добавлена строка лога
        appContainer = AppDataContainer(this)
        DatabaseInitializer.initialize(this)
        Log.d("LangApplication", "Initializer successfully called")
    }
}