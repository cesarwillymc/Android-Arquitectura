package com.doapps.android.weatherapp.domain.datasource.searchCities

import com.algolia.search.saas.places.PlacesClient
import com.algolia.search.saas.places.PlacesQuery
import com.doapps.android.weatherapp.domain.ExceptionGeneral
import com.doapps.android.weatherapp.domain.SafeApiRequest
import com.doapps.android.weatherapp.domain.model.SearchResponse
import com.doapps.android.weatherapp.utils.extensions.tryCatch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.google.gson.JsonElement

import com.google.gson.JsonParser





class SearchCitiesRemoteDataSource(private val client: PlacesClient) :
    SafeApiRequest() {

    suspend fun getCityWithQuery(query: String): SearchResponse =
        suspendCancellableCoroutine { continuation ->
            val algoliaQuery = PlacesQuery(query)
                .setLanguage("en")
                .setHitsPerPage(25)
            client.searchAsync(algoliaQuery) { json, exception ->
                if (exception == null) {
                    tryCatch(
                        tryBlock = {
                            val parser = JsonParser()
                            val mJson: JsonElement = parser.parse(json.toString())
                            val data =  Gson().fromJson(mJson, SearchResponse::class.java)

                            if (data?.hits != null)
                                continuation.resume(data)
                        },
                        catchBlock = {
                            continuation.resumeWithException(ExceptionGeneral("Sucedio un error al obtener ciudad"))
                        }
                    )
                } else
                    continuation.resumeWithException(
                        ExceptionGeneral(
                            exception.message ?: "Sucedio un error", exception.statusCode
                        )
                    )
            }


        }
}
