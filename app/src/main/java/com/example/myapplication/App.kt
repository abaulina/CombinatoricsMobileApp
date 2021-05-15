package com.example.myapplication

import android.app.Application
import com.yariksoffice.lingver.Lingver
import java.util.*

class App : Application() {

    @Suppress("UNUSED_VARIABLE")
    override fun onCreate() {
        super.onCreate()
        val systemLanguage : String = Locale.getDefault().language
        Lingver.init(this, systemLanguage)
    }

    companion object {
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_RUSSIAN = "ru"
    }
}