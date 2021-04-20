package com.doapps.android.weatherapp.module

import com.doapps.android.domain.usecase.CurrentWeatherUseCase
import com.doapps.android.domain.usecase.ForecastUseCase
import com.doapps.android.domain.usecase.SearchCitiesUseCase
import com.doapps.android.domain.repo.currentweather.CurrentWeatherRepository
import com.doapps.android.domain.repo.forecast.ForecastRepository
import com.doapps.android.domain.repo.searchcities.SearchCitiesRepository
import org.koin.dsl.module

val useCaseModule= module {
    factory { CurrentWeatherUseCase(get<CurrentWeatherRepository>()) }
    factory { ForecastUseCase(get<ForecastRepository>()) }
    factory { SearchCitiesUseCase(get<SearchCitiesRepository>()) }
}