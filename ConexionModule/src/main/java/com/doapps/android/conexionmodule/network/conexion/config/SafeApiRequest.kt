package com.doapps.android.conexionmodule.network.conexion.config

import com.doapps.android.conexionmodule.network.conexion.ExceptionGeneral
import org.json.JSONObject
import retrofit2.Response


abstract class SafeApiRequest {
    suspend fun <T:Any> apiRequest(call: suspend () -> Response<T>): T {
        try{
            val response = call.invoke()
            if (response.isSuccessful){
                return response.body()!!
            }else{

                val classError=JSONObject(response.errorBody()!!.string()).getJSONObject("error")

                throw ExceptionGeneral(classError.getString("message").toString(),classError.getString("statusCode").toInt())
            }
        }catch (e:Exception){
            throw ExceptionGeneral(e.message?:"Ocurrio un error, intente conectarse mas tarde")
        }
    }
}