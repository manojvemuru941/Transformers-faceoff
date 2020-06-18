package com.manoj.transformersae.util

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.manoj.transformersae.App
import com.manoj.transformersae.R
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
object AppUtill {
    const val DATABASE_NAME = "bot_db"
    var MAX_RETRIES = 3L
    var VIEW_TYPE_KEY = "VIEW_TYPE"
    var BOT_MODEL_KEY = "BOT"
    var TEAM_A_KEY = "A"
    var TEAM_D_KEY = "D"
    private const val PREFERENCE_NAME = "PREF"
    private const val PREFERENCE_TOKEN = "TOKEN"
    private const val INITIAL_BACKOFF = 2000L
    private lateinit var appContext: Context
    @get:VisibleForTesting
    @set:VisibleForTesting
    @VisibleForTesting
    var isTesting = false
    fun saveToken(context: Context, token: String?) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(PREFERENCE_TOKEN, token)
        editor.apply()
    }

    fun getSavedToken(context: Context): String {
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PREFERENCE_TOKEN, "")
    }

    fun deleteToken(context: Context){
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(PREFERENCE_TOKEN).apply()
    }

    fun verifyAvailableNetwork(): Boolean {
        val connectivityManager =
                getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun bindImage(url: String?, target: ImageView, centerCrop: Boolean) {
        val drawable = ContextCompat.getDrawable(target.context, R.drawable.ic_image_24dp)
        val builder: DrawableRequestBuilder<String> = Glide.with(getAppContext())
                .load(url)
                .error(R.drawable.ic_broken_image_24dp)
                .placeholder(drawable)
                .crossFade()
        if (centerCrop) builder.centerCrop()
        builder.into(target)
    }
    /**
     * Provides String from resources
     */
    fun getStringResource(resId : Int) : String {
        return getAppContext().getString(resId)
    }

    fun init (context: Context) {
        appContext = context
    }

    /**
     * Provides App Context
     */
    fun getAppContext() : Context {
        return appContext
    }

    @PublishedApi
    internal inline fun Retrofit.Builder.callFactory(crossinline body: (Request) -> Call) =
            callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call = body(request)
            })

    @Suppress("NOTHING_TO_INLINE")
    inline fun Retrofit.Builder.delegatingCallFactory(delegate: dagger.Lazy<OkHttpClient>): Retrofit.Builder =
            callFactory {
                delegate.get().newCall(it)
            }

    enum class TYPE(val value: Int) {
        UPDATE(1), CREATE(2), VIEW(3);
    }
}