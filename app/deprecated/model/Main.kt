package com.doapps.android.weatherapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Main(

    @SerializedName( "temp")
    val temp: Double?,

    @SerializedName( "temp_min")
    var tempMin: Double?,

    @SerializedName( "grnd_level")
    val grndLevel: Double?,

    @SerializedName( "temp_kf")
    val tempKf: Double?,

    @SerializedName( "humidity")
    val humidity: Int?,

    @SerializedName( "pressure")
    val pressure: Double?,

    @SerializedName( "sea_level")
    val seaLevel: Double?,

    @SerializedName( "temp_max")
    var tempMax: Double?
) : Parcelable {

    fun getTempString(): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(): String {
        return humidity.toString() + "°"
    }

    fun getTempMinString(): String {
        return tempMin.toString().substringBefore(".") + "°"
    }

    fun getTempMaxString(): String {
        return tempMax.toString().substringBefore(".") + "°"
    }
}
