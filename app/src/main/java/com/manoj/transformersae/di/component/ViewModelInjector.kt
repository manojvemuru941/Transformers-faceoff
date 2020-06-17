package com.manoj.transformersae.di.component

import com.manoj.transformersae.di.module.AppModule
import com.manoj.transformersae.di.module.DBModule
import com.manoj.transformersae.di.module.HttpModule
import com.manoj.transformersae.ui.MainViewModel
import com.manoj.transformersae.ui.detailview.DetailViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Manoj Vemuru on 2020-06-16.
 * manojvemuru@gmail.com
 *
 *  Component providing inject() methods for View Model presenters.
 */
@Component(modules = [AppModule::class, DBModule::class, HttpModule::class])
@Singleton
interface ViewModelInjector {

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun appModule(appModule: AppModule): Builder
        fun appDbModule(dbModule: DBModule): Builder
        fun httpModule(httpModule: HttpModule): Builder
    }

    /**
     * Injects required dependencies into the specified ViewModel.
     * @param mainViewModel: MainViewModel in which to inject the dependencies
     */
    fun inject(mainViewModel: MainViewModel)

    /**
     * Injects required dependencies into the specified ViewModel.
     * @param detailViewModel: DetailViewModel in which to inject the dependencies
     */
    fun inject(detailViewModel: DetailViewModel)
}