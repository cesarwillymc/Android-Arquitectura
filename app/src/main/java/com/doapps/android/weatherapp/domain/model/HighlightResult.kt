package com.doapps.android.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class HighlightResult(

    @SerializedName( "country")
    val country: Country? = null,

    @SerializedName( "name")
    val name: Name? = null
)
