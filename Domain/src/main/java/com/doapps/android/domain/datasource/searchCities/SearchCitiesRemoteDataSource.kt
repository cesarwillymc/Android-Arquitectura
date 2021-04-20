package com.doapps.android.domain.datasource.searchCities

import com.algolia.search.saas.places.PlacesClient
import com.algolia.search.saas.places.PlacesQuery
import com.doapps.android.conexionmodule.network.conexion.ExceptionGeneral
import com.doapps.android.conexionmodule.network.conexion.config.SafeApiRequest
import com.doapps.android.conexionmodule.network.model.SearchResponse
import com.doapps.android.conexionmodule.utils.tryCatch
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import com.google.gson.JsonElement

import com.google.gson.JsonParser
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


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
