package com.manoj.transformersae.base

/**
 * Created by Manoj Vemuru on 2020-06-16.
 * manojvemuru@gmail.com
 */
sealed class ViewState {
    data class MainView(val value: Int = 0): ViewState()
    data class DetailView(val value: Int = 1): ViewState()
}