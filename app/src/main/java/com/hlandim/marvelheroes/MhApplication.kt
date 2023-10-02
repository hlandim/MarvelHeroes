package com.hlandim.marvelheroes

import android.app.Application
import com.hlandim.marvelheroes.di.mhModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MhApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MhApplication)
            modules(mhModule)
        }
    }
}
