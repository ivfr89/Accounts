package com.fernandez.players.core

import android.app.Application
import com.fernandez.players.domain.di.mainModule
import com.fernandez.players.domain.di.accountModule
import com.fernandez.players.domain.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application()
{


    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(listOf(retrofitModule, mainModule, accountModule))
        }

    }
}