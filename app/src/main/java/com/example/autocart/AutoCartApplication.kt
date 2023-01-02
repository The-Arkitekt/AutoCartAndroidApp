package com.example.autocart

import android.app.Application
import com.example.autocart.data.AppContainer
import com.example.autocart.data.DefaultAppContainer

class AutoCartApplication : Application() {
    /** AppContainer instance used by the rest of the classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}