package com.doapps.android.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @SerializedName( "hits")
    val hits: List<HitsItem?>? = null
)
