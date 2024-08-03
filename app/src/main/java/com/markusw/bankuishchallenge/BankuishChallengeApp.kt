package com.markusw.bankuishchallenge

import android.app.Application
import com.markusw.bankuishchallenge.di.appModule
import com.markusw.bankuishchallenge.di.mainModule
import com.markusw.bankuishchallenge.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BankuishChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BankuishChallengeApp)
            modules(
                appModule,
                networkModule,
                mainModule
            )
        }
    }

}