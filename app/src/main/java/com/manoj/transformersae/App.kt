package com.manoj.transformersae

import android.app.Application
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.init

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        init(this)

        AppUtill.isTesting = false
    }
}