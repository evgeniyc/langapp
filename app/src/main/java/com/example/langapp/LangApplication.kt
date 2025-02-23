package com.example.langapp

import android.app.Application
import android.util.Log
import com.example.langapp.data.AppContainer
import com.example.langapp.data.AppDataContainer
import com.example.langapp.data.database.DatabaseInitializer

class LangApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        Log.d("LangApplication", "onCreate called") // Добавлена строка лога
        appContainer = AppDataContainer(this)
        DatabaseInitializer.initialize(this)
    }
}