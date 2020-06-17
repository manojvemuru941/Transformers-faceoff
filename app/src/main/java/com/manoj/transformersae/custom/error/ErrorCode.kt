package com.manoj.transformersae.custom.error


/**
 * Created by Manoj Vemuru on 2019-06-29.
 * manojvemuru@gmail.com
 */

enum class ErrorCode(private val value: Int) {

    ERROR_400(400),
    ERROR_401(401),
    ERROR_403(403),
    ERROR_404(404),
    ERROR_410(410),
    ERROR_412(412),
    ERROR_422(422),
    ERROR_423(423),
    ERROR_426(426),
    ERROR_451(451),
    ERROR_477(477),
    ERROR_500(500),

    ERROR_42200(42200),
    ERROR_40001(40001);

    fun getValue() : Int {
        return value
    }
}