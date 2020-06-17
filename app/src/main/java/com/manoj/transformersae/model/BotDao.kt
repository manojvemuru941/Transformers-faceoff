package com.manoj.transformersae.model

import androidx.annotation.VisibleForTesting
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
@Dao
interface BotDao {

    @Query("SELECT * FROM botmodel")
    fun getAll(): Flowable<List<BotModel>>

    @Query("SELECT * FROM botmodel where id = :id")
    fun getBotById(id: String?): BotModel?

    @Insert
    suspend fun insertBot(botModel: BotModel?)

    @Insert
    fun insertAllBot(botModelList: List<BotModel?>?)

    @Delete
    suspend fun deleteBot(botModel: BotModel?)

    @Update
    suspend fun updateBot(botModel: BotModel?)

    @VisibleForTesting
    @Query("DELETE FROM botmodel")
    fun deleteAll()
}