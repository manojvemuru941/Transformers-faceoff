package com.manoj.transformersae.service

import com.manoj.transformersae.model.BotModel
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Manoj Vemuru on 2020-06-16.
 * manojvemuru@gmail.com
 */

interface RestService {

    @Headers("Content-type: application/json")
    @GET("{path}")
    fun getRequest(
            @Path("path") path: String
    ): Call<String>

    @Headers("Content-type: application/json")
    @POST("{path}")
    fun postRequest(
            @Path("path") path: String,
            @Header("Authorization") authorization: String?,
            @Body body: BotModel
    ): Call<BotModel>

    @Headers("Content-type: application/json")
    @PUT("{path}")
    fun putRequest(
            @Path("path") path: String,
            @Header("Authorization") authorization: String?,
            @Body body: BotModel
    ): Call<BotModel>

    @Headers("Content-type: application/json")
    @DELETE("{path}")
    fun deleteRequest(
            @Path("path") path: String,
            @Header("Authorization") authorization: String?
    ): Call<Void>
}