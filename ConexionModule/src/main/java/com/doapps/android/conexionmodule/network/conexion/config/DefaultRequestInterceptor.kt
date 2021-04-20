package com.doapps.android.conexionmodule.network.conexion.config


import com.doapps.android.conexionmodule.BuildConfig
import com.doapps.android.conexionmodule.db.dataStore.DataStoreManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.Response



class DefaultRequestInterceptor(private val dataStore: DataStoreManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter(BuildConfig.API_KEY_QUERY, BuildConfig.API_KEY_VALUE)
            .build()
        val request = chain.request().newBuilder().url(url).addHeader("Authorization", getTokenData() ).build()
        return chain.proceed(request)

    }

    private fun getTokenData(): String {
        var token=""
        GlobalScope.launch(Dispatchers.Main) {
            token= dataStore.getStringDataStore("token").first()
        }
        return token
    }
}
