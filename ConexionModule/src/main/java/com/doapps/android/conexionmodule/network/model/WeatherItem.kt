package com.doapps.android.conexionmodule.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherItem(

    @SerializedName("icon")
    val icon: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("main")
    val main: String?,

    @SerializedName("id")
    val id: Int?
) : Parcelable {

    fun getDescriptionText(): String? {
        return description?.capitalize()
    }
}
