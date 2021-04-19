package com.doapps.android.weatherapp.di.module

import com.doapps.android.weatherapp.repo.CurrentWeatherRepository
import com.doapps.android.weatherapp.repo.ForecastRepository
import com.doapps.android.weatherapp.repo.SearchCitiesRepository
import org.koin.dsl.module

val repositoryModule= module {
    factory { CurrentWeatherRepository(get(),get()) }
    factory { ForecastRepository(get(),get()) }
    factory { SearchCitiesRepository(get(),get()) }
}