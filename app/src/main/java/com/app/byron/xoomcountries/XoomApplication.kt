package com.app.byron.xoomcountries

import android.app.Application
import com.app.byron.xoomcountries.di.mainModule
import org.koin.android.ext.android.startKoin

class XoomApplication : Application() {

    override fun onCreate(){
        super.onCreate()
        startKoin(this, listOf(mainModule))
    }

}