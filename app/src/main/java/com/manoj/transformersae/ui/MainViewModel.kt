package com.manoj.transformersae.ui

import androidx.lifecycle.MutableLiveData
import com.manoj.transformersae.R
import com.manoj.transformersae.base.BaseViewModel
import com.manoj.transformersae.base.ResourceState
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.util.AppUtill
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
class MainViewModel : BaseViewModel() {
    var allBots: List<BotModel> = ArrayList()

    val mutableBotWarResponseLiveData = MutableLiveData<String>()
    val mutableBotFragmentLiveData = MutableLiveData<List<BotModel>>()
    val mutableBotListLiveData = MutableLiveData<List<BotModel>>()
    fun requestToRefreshList() {
        disposables.add(transformersRepository.loadBotList(appDBService.botDao())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onAsyncStart() }
                .subscribe({
                    onAsyncFinish()
                    allBots = it
                    mutableBotListLiveData.value = it
                },
                        { err ->
                            onError(err.localizedMessage)
                            onAsyncFinish()
                        })
        )
    }

    fun getBotById(id: String): BotModel? {
        for (botModel in allBots) {
            if (botModel.id == id) {
                return botModel
            }
        }
        return null
    }


    fun startWar() {
        disposables.add(transformersRepository.launchWar(allBots)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onAsyncStart() }
                .subscribe({
                    when (it) {
                        is ResourceState.Success<*> -> {
                            mutableBotWarResponseLiveData.value = it.data as String
                        }
                        else -> {
                            onError(AppUtill.getStringResource(R.string.something_wrong))
                        }
                    }
                    onAsyncFinish()
                }, { err ->
                    onError(err.localizedMessage)
                    onAsyncFinish()
                })
        )
    }

    fun deleteBot(botModel: BotModel) {
        disposables.add(transformersRepository.deleteTransformer(botModel, AppUtill.getSavedToken(AppUtill.getAppContext()))
                .observeOn(Schedulers.io())
                .flatMap { state ->  transformersRepository.removeBotModel(state, botModel, appDBService.botDao())}
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onAsyncStart() }
                .subscribe({
                    when(it) {
                        is ResourceState.Success<*>  -> {
                            requestToRefreshList()
                        }
                        is ResourceState.ServerError -> {
                            onServerError(it.throwable)
                        }
                        is ResourceState.Failure -> {
                            onError(AppUtill.getStringResource(R.string.something_wrong))
                        }
                    }
                    onAsyncFinish()
                }, { err ->
                    onError(err.localizedMessage)
                    onAsyncFinish()
                })
        )
    }
}