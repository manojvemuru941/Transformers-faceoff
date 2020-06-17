package com.manoj.transformersae.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manoj.transformersae.model.BotDao
import com.manoj.transformersae.model.BotModel

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
@Database(entities = [BotModel::class], version = 1, exportSchema = false)
abstract class AppDBService : RoomDatabase() {
    abstract fun botDao(): BotDao
}