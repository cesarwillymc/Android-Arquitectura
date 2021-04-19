package com.doapps.android.weatherapp.di.module

import com.doapps.android.weatherapp.domain.usecase.CurrentWeatherUseCase
import com.doapps.android.weatherapp.domain.usecase.ForecastUseCase
import com.doapps.android.weatherapp.domain.usecase.SearchCitiesUseCase
import org.koin.dsl.module

val useCaseModule= module {
    factory { CurrentWeatherUseCase(get()) }
    factory { ForecastUseCase(get()) }
    factory { SearchCitiesUseCase(get()) }
}