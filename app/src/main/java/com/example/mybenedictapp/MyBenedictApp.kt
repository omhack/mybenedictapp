package com.example.mybenedictapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyBenedictApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}