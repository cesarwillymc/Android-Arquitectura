package com.doapps.android.conexionmodule.network.conexion


class ExceptionGeneral( messageD:String,codigo:Int=0): Exception(if (messageD.contains("handshake") || messageD.contains("Unable to resolve host")) "Revisa tu conexión a internet" else messageD){
    var code:Int=0
    init {
        code=codigo
    }
}

