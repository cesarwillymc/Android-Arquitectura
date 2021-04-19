package com.doapps.android.weatherapp.utils

/**
 * Created by Furkan on 2019-10-26
 */

interface Mapper<R, D> {
    fun mapFrom(type: R): D
}
