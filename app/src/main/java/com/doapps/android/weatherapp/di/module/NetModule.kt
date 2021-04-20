package com.doapps.android.weatherapp.di.module

import com.algolia.search.saas.places.PlacesClient
import com.doapps.android.conexionmodule.network.conexion.WeatherAppAPI
import com.doapps.android.conexionmodule.network.conexion.config.DefaultRequestInterceptor
import com.doapps.android.conexionmodule.network.conexion.config.HttpConfiguration
import com.doapps.android.weatherapp.WeatherApp
import com.doapps.android.weatherapp.core.Constants
import org.koin.dsl.module

val netModule = module {

    val places: PlacesClient =
        PlacesClient(Constants.AlgoliaKeys.APPLICATION_ID, Constants.AlgoliaKeys.SEARCH_API_KEY)
    factory<PlacesClient> { places }
    factory {  DefaultRequestInterceptor(WeatherApp.getContextApp()) }
    factory {  HttpConfiguration(get()) }
    factory<WeatherAppAPI> {
        WeatherAppAPI.invoke(get())
    }
}
