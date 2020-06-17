package com.manoj.transformersae.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule constructor(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext() = context
}