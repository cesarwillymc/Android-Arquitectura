package com.doapps.android.conexionmodule.network.conexion.config

import android.os.Environment
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class HttpConfiguration(private val defaultRequestInterceptor: DefaultRequestInterceptor) {
    private val timeOut = 10
    fun onCreate(): OkHttpClient {
        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
        return OkHttpClient.Builder()
            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .addInterceptor(defaultRequestInterceptor)
            .cache(cache)
            .build()
    }
}