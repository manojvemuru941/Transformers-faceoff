package com.manoj.transformersae.di.module

import android.content.Context
import androidx.room.Room
import com.manoj.transformersae.service.AppDBService
import com.manoj.transformersae.util.AppUtill.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Manoj Vemuru on 2020-06-16.
 * manojvemuru@gmail.com
 */
@Module

class DBModule {

    @Provides
    @Singleton
    fun provideAppDB(context: Context): AppDBService = Room.databaseBuilder(context, AppDBService::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideBotDao(appDBService: AppDBService) = appDBService.botDao()
}
