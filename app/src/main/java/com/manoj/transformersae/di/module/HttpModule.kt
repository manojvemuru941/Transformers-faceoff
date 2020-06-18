package com.manoj.transformersae.di.module

import com.google.gson.GsonBuilder
import com.manoj.transformersae.BuildConfig
import com.manoj.transformersae.BuildConfig.BASE_URL
import com.manoj.transformersae.service.RestService
import com.manoj.transformersae.service.TransformersRepository
import com.manoj.transformersae.util.AppUtill.callFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Manoj Vemuru on 2020-06-16.
 * manojvemuru@gmail.com
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object HttpModule {
    private const val timeOut = 20L //20Secs//
    /**
     * Provides the Web Api service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Api service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): TransformersRepository {
        return TransformersRepository(retrofit.create(RestService::class.java))
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(client: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .callFactory { client.get().newCall(it) }
                .build()
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(timeOut, TimeUnit.SECONDS)
            connectTimeout(timeOut, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(this)
                }
            }
        }.build()
    }
}