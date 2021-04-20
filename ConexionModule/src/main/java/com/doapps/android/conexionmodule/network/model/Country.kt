package com.doapps.android.conexionmodule.network.model

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName( "matchLevel")
    val matchLevel: String? = null,

    @SerializedName( "value")
    val value: String? = null,

    @SerializedName( "matchedWords")
    val matchedWords: List<Any?>? = null
)
