package com.example.todolistpratillipi.ui.theme.di


import android.app.Application
import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp
class MyApplication : Application()
{


    override fun onCreate() {
        super.onCreate()
    }

}