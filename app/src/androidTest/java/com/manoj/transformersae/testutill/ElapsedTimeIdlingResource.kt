package com.manoj.transformersae.testutill

import androidx.test.espresso.IdlingResource

/**
 * Created by Manoj Vemuru on 2020-06-17.
 * manojvemuru@gmail.com
 */
class ElapsedTimeIdlingResource(private val waitingTime: Long) : IdlingResource {
    private var startTime: Long = System.currentTimeMillis()
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String? {
        return ElapsedTimeIdlingResource::class.java.name + ":" + waitingTime
    }

    override fun isIdleNow(): Boolean {
        val elapsed = System.currentTimeMillis() - startTime
        val idle = elapsed >= waitingTime
        if (idle) {
            resourceCallback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = resourceCallback
    }
}