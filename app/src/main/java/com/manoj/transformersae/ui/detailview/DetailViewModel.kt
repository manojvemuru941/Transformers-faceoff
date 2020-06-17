package com.manoj.transformersae.ui.detailview

import androidx.lifecycle.viewModelScope
import com.manoj.transformersae.R
import com.manoj.transformersae.base.BaseViewModel
import com.manoj.transformersae.base.ResourceState
import com.manoj.transformersae.base.ViewState
import com.manoj.transformersae.custom.SingleLiveEvent
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.getAppContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.async

/**
 * Created by Manoj Vemuru on 2018-09-22.
 */
class DetailViewModel : BaseViewModel() {
    private lateinit var mBotModel: BotModel
    val mutableBotLiveData = SingleLiveEvent<Result<BotModel>>()

    fun getBotModel(): BotModel {
        return mBotModel
    }

    fun setBotModel(botModel: BotModel) {
        this.mBotModel = botModel
    }

    fun save(botModel: BotModel) {
        disposables.add(transformersRepository.storeTransformer(botModel, AppUtill.getSavedToken(getAppContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onAsyncStart() }
                .subscribe({
                    when (it) {
                        is ResourceState.Success<*> -> {
                            viewModelScope.async {
                                appDBService.botDao().insertBot(it.data as BotModel)
                            }
                            mutableViewType.value = ViewState.MainView()
                        }
                        is ResourceState.ServerError -> {
                            onAsyncFinish()
                            onServerError(it.throwable)
                        }
                        is ResourceState.Failure -> {
                            onAsyncFinish()
                            onError(AppUtill.getStringResource(R.string.something_wrong))
                        }
                    }
                },
                        { err ->
                            onError(err.localizedMessage)
                            onAsyncFinish()
                        })
        )
    }

    fun update(botModel: BotModel) {
        disposables.add(transformersRepository.updateTransformer(botModel, AppUtill.getSavedToken(getAppContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onAsyncStart() }
                .subscribe({
                    when (it) {
                        is ResourceState.Success<*> -> {
                            viewModelScope.async {
                                appDBService.botDao().updateBot(it.data as BotModel)
                            }
                            mutableViewType.value = ViewState.MainView()
                        }
                        is ResourceState.ServerError -> {
                            onAsyncFinish()
                            onServerError(it.throwable)
                        }
                        is ResourceState.Failure -> {
                            onAsyncFinish()
                            onError(AppUtill.getStringResource(R.string.something_wrong))
                        }
                    }
                },
                        { err ->
                            onError(err.localizedMessage)
                            onAsyncFinish()
                        })
        )
    }
}
