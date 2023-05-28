package com.example.story

import android.app.Application

object Locator {
    private var application: Application? = null

    fun initWith(application: Application) {
        Locator.application = application
    }
}