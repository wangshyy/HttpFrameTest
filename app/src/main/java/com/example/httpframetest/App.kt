package com.example.httpframetest

import android.app.Application
import com.example.httpframetest.core.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 *  author : wsy
 *  date   : 2023/2/23
 *  desc   :
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //启动koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(appModule)  //加载module
        }
    }
}