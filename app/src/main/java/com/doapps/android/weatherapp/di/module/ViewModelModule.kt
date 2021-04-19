package com.doapps.android.weatherapp.di.module


import com.doapps.android.weatherapp.ui.dashboard.DashboardFragmentViewModel
import com.doapps.android.weatherapp.ui.dashboard.forecast.ForecastItemViewModel
import com.doapps.android.weatherapp.ui.main.MainActivityViewModel
import com.doapps.android.weatherapp.ui.search.SearchViewModel
import com.doapps.android.weatherapp.ui.search.result.SearchResultItemViewModel
import com.doapps.android.weatherapp.ui.splash.SplashFragmentViewModel
import com.doapps.android.weatherapp.ui.weather_detail.WeatherDetailViewModel
import com.doapps.android.weatherapp.ui.weather_detail.weatherHourOfDay.WeatherHourOfDayItemViewModel

import org.koin.dsl.module


val viewModelModule = module {
    factory { WeatherDetailViewModel(get()) }
    factory { SplashFragmentViewModel(get()) }
    factory { SearchViewModel(get(),get()) }
    factory { DashboardFragmentViewModel(get(),get(),get()) }

    factory { MainActivityViewModel() }
    factory { WeatherHourOfDayItemViewModel() }
    factory { SearchResultItemViewModel() }
    factory { ForecastItemViewModel() }
}

