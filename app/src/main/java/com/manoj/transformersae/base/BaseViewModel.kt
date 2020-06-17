package com.manoj.transformersae.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manoj.transformersae.R
import com.manoj.transformersae.custom.SingleLiveEvent
import com.manoj.transformersae.custom.error.CustomThrowable
import com.manoj.transformersae.custom.error.ErrorCode
import com.manoj.transformersae.di.component.DaggerViewModelInjector
import com.manoj.transformersae.di.component.ViewModelInjector
import com.manoj.transformersae.di.module.AppModule
import com.manoj.transformersae.di.module.DBModule
import com.manoj.transformersae.di.module.HttpModule
import com.manoj.transformersae.service.AppDBService
import com.manoj.transformersae.service.TransformersRepository
import com.manoj.transformersae.ui.MainViewModel
import com.manoj.transformersae.ui.detailview.DetailViewModel
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.getAppContext
import com.manoj.transformersae.util.AppUtill.getStringResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Manoj Vemuru on 2020-06-16.
 * manojvemuru@gmail.com
 */

abstract class BaseViewModel : ViewModel() {

    open val errorClickListener = View.OnClickListener { }

    val mutableViewType: SingleLiveEvent<ViewState> =
            SingleLiveEvent()//Do not observe this, consume the events using handleMutualViewType from baseactivity/basefragment
    val mutableDeviceBackPressed: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val errorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    protected var disposables = ArrayList<Disposable>()
    @Inject
    protected lateinit var transformersRepository: TransformersRepository
    @Inject
    protected lateinit var appDBService: AppDBService

    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .appModule(AppModule(getAppContext()))
            .appDbModule(DBModule())
            .httpModule(HttpModule)
            .build()
    init {
        inject()
    }
    private fun inject() {
        when(this) {
            is MainViewModel -> injector.inject(this)
            is DetailViewModel -> injector.inject(this)
        }
    }

    fun getToken() {
        if(AppUtill.getSavedToken(getAppContext())?.isNullOrEmpty()) {
            disposables.add(transformersRepository.getToken()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { onAsyncStart() }
                    .subscribe({
                        when (it) {
                            is ResourceState.Success<*> -> {
                                val token = it.data as String
                                AppUtill.saveToken(getAppContext(),"Bearer $token")
                            }
                            is ResourceState.ServerError -> {
                                onAsyncFinish()
                                onServerError(it.throwable)
                            }
                            is ResourceState.Failure -> {
                                onAsyncFinish()
                                onError(getStringResource(R.string.something_wrong))
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
    /**
     * Shows Loading ProgressBar
     */
    fun onAsyncStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    /**
     * Hides ProgressBar
     */
    fun onAsyncFinish() {
        loadingVisibility.value = View.GONE
    }

    /**
     * Handler Server Errors
     */
    open fun onServerError(customThrowable: CustomThrowable) {
        when (customThrowable.errorCodeEnum) {
            ErrorCode.ERROR_401 -> {
                onError(getStringResource(R.string.token_not_available))
            }

            ErrorCode.ERROR_500 -> {
                onError(getStringResource(R.string.something_wrong))
            }
            else -> {
                onError(getStringResource(R.string.something_wrong))
            }
        }
    }

    /**
     * Shows Error Message
     */
    open fun onError(err: String) {
        loadingVisibility.value = View.GONE
        errorMessage.value = err
    }

    open fun onDeviceBackPressed() {
        mutableDeviceBackPressed.postValue(true)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.forEach {
            it.dispose()
        }
        disposables.clear()
    }
}