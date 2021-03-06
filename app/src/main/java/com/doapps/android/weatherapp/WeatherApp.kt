package com.doapps.android.weatherapp

import android.app.Application
import android.content.Context
import android.os.Build
import com.bugsnag.android.Bugsnag
import com.doapps.android.weatherapp.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import timber.log.Timber

class WeatherApp : Application() {

    companion object{
        private lateinit var instance:WeatherApp
        fun getContextApp(): Context =instance
        fun setInstance(instance:WeatherApp){
            this.instance=instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        //Stop koin for if its open
        stopKoin()
        setInstance(this)

        if (BuildConfig.DEBUG && !isRoboUnitTest()) {
            Timber.plant(Timber.DebugTree())
        }
        //Init bugsnag for check errors
        Bugsnag.start(this)

        //Init koin dependency injection
        startKoin {
            // Koin Android logger
            androidLogger()

            //inject Android context
            androidContext(this@WeatherApp)
            // use modules
            modules(module(override = true) {
               modules(listOf(aplicationModule,dbModule, repositoryModule, useCaseModule,
                   viewModelModule, dataSourceModule,netModule))
            })
        }
    }

    private fun isRoboUnitTest(): Boolean {
        return "robolectric" == Build.FINGERPRINT
    }
}
