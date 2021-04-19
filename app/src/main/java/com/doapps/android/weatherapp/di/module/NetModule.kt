package com.doapps.android.weatherapp.di.module

import com.algolia.search.saas.places.PlacesClient
import com.doapps.android.weatherapp.core.Constants
import com.doapps.android.weatherapp.domain.WeatherAppAPI
import org.koin.dsl.module

val netModule = module {




    val places: PlacesClient =
        PlacesClient(Constants.AlgoliaKeys.APPLICATION_ID, Constants.AlgoliaKeys.SEARCH_API_KEY)
    factory<PlacesClient> { places }

    factory<WeatherAppAPI> {
        WeatherAppAPI.invoke()
    }
}
