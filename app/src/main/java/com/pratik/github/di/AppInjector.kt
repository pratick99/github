package com.pratik.github.di

import com.pratik.github.App

object AppInjector {

    fun init(application: App) {
        DaggerAppComponent
            .builder()
            .application(application)
            .build()
            .inject(application)
    }
}
