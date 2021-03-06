package com.doapps.android.weatherapp.di.module


import com.doapps.android.weatherapp.db.WeatherDatabase
import org.koin.dsl.module


val dbModule = module {

    single<WeatherDatabase> {  WeatherDatabase(get()) }
    factory {  get<WeatherDatabase>().forecastDao() }
    factory {  get<WeatherDatabase>().currentWeatherDao() }
    factory {  get<WeatherDatabase>().citiesForSearchDao() }
}

