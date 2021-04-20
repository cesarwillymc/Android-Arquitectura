package com.doapps.android.weatherapp.module

import com.doapps.android.domain.datasource.currentWeather.CurrentWeatherLocalDataSource
import com.doapps.android.domain.datasource.currentWeather.CurrentWeatherRemoteDataSource
import com.doapps.android.domain.datasource.forecast.ForecastLocalDataSource
import com.doapps.android.domain.datasource.forecast.ForecastRemoteDataSource
import com.doapps.android.domain.datasource.searchCities.SearchCitiesLocalDataSource
import com.doapps.android.domain.datasource.searchCities.SearchCitiesRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { CurrentWeatherLocalDataSource(get()) }
    factory { CurrentWeatherRemoteDataSource(get()) }

    factory { ForecastLocalDataSource(get()) }
    factory { ForecastRemoteDataSource(get()) }

    factory { SearchCitiesLocalDataSource(get()) }
    factory { SearchCitiesRemoteDataSource(get()) }
}