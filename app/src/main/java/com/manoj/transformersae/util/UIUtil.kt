package com.manoj.transformersae.util

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.manoj.transformersae.R

/**
 * Created by Manoj Vemuru on 2019-10-29.
 * manoj@ardentsoftsol.com
 */

class UIUtil {
    companion object {
        fun getAlertDialog(context: Activity, title: String, isCancelable:Boolean = false) : AlertDialog.Builder {
            return AlertDialog.Builder(context, R.style.Dialog_Theme)
                .setTitle(title)
                .setCancelable(isCancelable)
        }

        fun pxFromDp(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }
    }
}