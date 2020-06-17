package com.manoj.transformersae.base

import com.manoj.transformersae.custom.error.CustomThrowable

/**
 * Created by Manoj Vemuru on 2019-06-29.
 * manoj@ardentsoftsol.com
 */

sealed class ResourceState{
    data class Success<out T>(val data: T?) : ResourceState()
    data class ServerError(val throwable: CustomThrowable) : ResourceState()
    data class Failure(val throwable: Throwable) : ResourceState()
}