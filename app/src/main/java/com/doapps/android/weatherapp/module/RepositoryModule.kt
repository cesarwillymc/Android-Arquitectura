package com.doapps.android.weatherapp.module



import com.doapps.android.domain.repo.currentweather.CurrentWeatherRepository
import com.doapps.android.domain.repo.currentweather.CurrentWeatherRepositoryImpl
import com.doapps.android.domain.repo.forecast.ForecastRepository
import com.doapps.android.domain.repo.forecast.ForecastRepositoryImpl
import com.doapps.android.domain.repo.searchcities.SearchCitiesRepository
import com.doapps.android.domain.repo.searchcities.SearchCitiesRepositoryImpl
import org.koin.dsl.module

val repositoryModule= module {
    factory<CurrentWeatherRepository> { CurrentWeatherRepositoryImpl(get(),get()) }
    factory<ForecastRepository> { ForecastRepositoryImpl(get(),get()) }
    factory<SearchCitiesRepository> { SearchCitiesRepositoryImpl(get(),get()) }
}