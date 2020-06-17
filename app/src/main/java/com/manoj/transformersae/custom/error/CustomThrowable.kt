package com.manoj.transformersae.custom.error


import com.google.gson.Gson
import com.manoj.transformersae.R
import com.manoj.transformersae.util.AppUtill.getStringResource


/**
 * Created by Manoj Vemuru on 2019-06-29.
 * manojvemuru@gmail.com
 */

class CustomThrowable(private val errorBody : String?, private var errorCode: Int) : Throwable(errorBody) {

    var errorMesasge:String? = errorBody

    init {
        processError()
    }
    var errorCodeEnum: ErrorCode = when(errorCode) {
        ErrorCode.ERROR_400.getValue() -> ErrorCode.ERROR_400
        ErrorCode.ERROR_401.getValue() -> ErrorCode.ERROR_401
        ErrorCode.ERROR_403.getValue() -> ErrorCode.ERROR_403
        ErrorCode.ERROR_404.getValue() -> ErrorCode.ERROR_404
        ErrorCode.ERROR_410.getValue() -> ErrorCode.ERROR_410
        ErrorCode.ERROR_412.getValue() -> ErrorCode.ERROR_412
        ErrorCode.ERROR_422.getValue() -> ErrorCode.ERROR_422
        ErrorCode.ERROR_423.getValue() -> ErrorCode.ERROR_423
        ErrorCode.ERROR_426.getValue() -> ErrorCode.ERROR_426
        ErrorCode.ERROR_451.getValue() -> ErrorCode.ERROR_451
        ErrorCode.ERROR_477.getValue() -> ErrorCode.ERROR_477
        ErrorCode.ERROR_500.getValue() -> ErrorCode.ERROR_500

        else -> ErrorCode.ERROR_500
    }

    private fun processError() {
        try {
            val gson = Gson()
            val errorList : List<Error> = gson.fromJson(errorBody?.toString(), Array<Error>::class.java).toList()

            if(errorList.isNotEmpty()) {
               //TODO:handling custom errors
            }
        } catch (ex : Exception) {
            errorMesasge = getStringResource(R.string.something_wrong)
        }

    }
}