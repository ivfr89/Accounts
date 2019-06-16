package com.fernandez.accounts.core

import android.app.Application
import com.fernandez.accounts.domain.di.mainModule
import com.fernandez.accounts.domain.di.accountModule
import com.fernandez.accounts.domain.di.retrofitModule
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