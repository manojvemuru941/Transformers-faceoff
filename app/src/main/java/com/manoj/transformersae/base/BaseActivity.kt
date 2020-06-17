package com.manoj.transformersae.base

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.manoj.transformersae.R
import com.manoj.transformersae.ui.MainActivity
import com.manoj.transformersae.ui.detailview.ItemDetailActivity
import com.manoj.transformersae.util.AppUtill.getStringResource
import com.manoj.transformersae.util.UIUtil

/**
 * Created by Manoj Vemuru on 2019-06-29.
 * manojvemuru@gmail.com
 */

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    abstract fun setViewModel(): T

    protected val viewModel: T by lazy { setViewModel() }

    private var errorSnackbar: Snackbar? = null
    protected val progressBar: ProgressBar by lazy { setProgressView() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        viewModel.getToken()
        viewModel.mutableViewType.observe(this, nextViewObserver)
    }

    override fun onStart() {
        super.onStart()
        registerToShowError()
    }

    open fun registerToShowError() {
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {

                val builder = UIUtil.getAlertDialog(this, getStringResource(R.string.error), true)
                builder.setMessage(errorMessage)
                        .setPositiveButton(getStringResource(R.string.ok)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()

            } else
                hideError()
        })
    }
    /**
     * Hides Error Message
     */
    open fun hideError() {
        errorSnackbar?.dismiss()
    }

    open fun setProgressView(): ProgressBar = ProgressBar(this)

    @CallSuper
    open fun handleMutualViewType(viewTypeState: ViewState) {
        when (viewTypeState) {
            is ViewState.MainView -> showMain()
            is ViewState.DetailView -> showDetail()
        }
    }

    protected fun showMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    protected fun showDetail() {
        val homeIntent = Intent(this, ItemDetailActivity::class.java)
        startActivity(homeIntent)
        finish()
    }

    public fun getBaseViewModel(): BaseViewModel = viewModel

    fun goBack() {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        viewModel.onDeviceBackPressed()
    }

    private val nextViewObserver = Observer<ViewState> {
        handleMutualViewType(it)
    }
}