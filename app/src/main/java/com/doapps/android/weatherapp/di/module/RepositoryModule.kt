package com.doapps.android.weatherapp.di.module

import com.doapps.android.weatherapp.repo.currentweather.CurrentWeatherRepository
import com.doapps.android.weatherapp.repo.currentweather.CurrentWeatherRepositoryImpl
import com.doapps.android.weatherapp.repo.forecast.ForecastRepository
import com.doapps.android.weatherapp.repo.forecast.ForecastRepositoryImpl
import com.doapps.android.weatherapp.repo.searchcities.SearchCitiesRepository
import com.doapps.android.weatherapp.repo.searchcities.SearchCitiesRepositoryImpl
import org.koin.dsl.module

val repositoryModule= module {
    factory<CurrentWeatherRepository> { CurrentWeatherRepositoryImpl(get(),get()) }
    factory<ForecastRepository> { ForecastRepositoryImpl(get(),get()) }
    factory<SearchCitiesRepository> { SearchCitiesRepositoryImpl(get(),get()) }
}