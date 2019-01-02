package com.ajin.byron.xoomchallenge

import android.app.Application
import com.ajin.byron.xoomchallenge.di.mainModule
import org.koin.android.ext.android.startKoin

class XoomApplication : Application() {

    override fun onCreate(){
        super.onCreate()
        startKoin(this, listOf(mainModule))
    }

}