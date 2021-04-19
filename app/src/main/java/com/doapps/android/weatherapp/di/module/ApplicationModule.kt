package com.doapps.android.weatherapp.di.module

import android.content.Context
import com.doapps.android.weatherapp.WeatherApp
import org.koin.dsl.module


val aplicationModule = module {
    factory {  WeatherApp.getContextApp().getSharedPreferences("wheater-appa", Context.MODE_PRIVATE) }
}