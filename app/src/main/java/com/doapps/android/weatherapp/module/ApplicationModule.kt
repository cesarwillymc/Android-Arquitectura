package com.doapps.android.weatherapp.module

import android.content.Context
import com.doapps.android.conexionmodule.db.dataStore.DataStoreManager
import com.doapps.android.weatherapp.WeatherApp
import org.koin.dsl.module


val aplicationModule = module {
    factory {  WeatherApp.getContextApp().getSharedPreferences("wheater-appa", Context.MODE_PRIVATE) }
    single {  DataStoreManager(WeatherApp.getContextApp()) }
}