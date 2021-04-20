package com.doapps.android.weatherapp.di.module

import com.doapps.android.weatherapp.domain.usecase.CurrentWeatherUseCase
import com.doapps.android.weatherapp.domain.usecase.ForecastUseCase
import com.doapps.android.weatherapp.domain.usecase.SearchCitiesUseCase
import com.doapps.android.weatherapp.repo.currentweather.CurrentWeatherRepository
import com.doapps.android.weatherapp.repo.forecast.ForecastRepository
import com.doapps.android.weatherapp.repo.searchcities.SearchCitiesRepository
import org.koin.dsl.module

val useCaseModule= module {
    factory { CurrentWeatherUseCase(get<CurrentWeatherRepository>()) }
    factory { ForecastUseCase(get<ForecastRepository>()) }
    factory { SearchCitiesUseCase(get<SearchCitiesRepository>()) }
}