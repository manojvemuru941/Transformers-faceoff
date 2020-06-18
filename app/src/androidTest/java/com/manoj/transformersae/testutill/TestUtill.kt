package com.manoj.transformersae.testutill

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.manoj.transformersae.model.BotModel
import org.apache.commons.io.IOUtils
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

/**
 * Created by Manoj Vemuru on 2018-09-22.
 */
class TestUtill {
    companion object {
        fun parseTransformersResponseToList(response: String): List<BotModel> {
            val jsonArray = JSONObject(response).getJSONArray("transformers")
            val listType = object : TypeToken<List<BotModel>>() {}.type
            return getGson().fromJson(jsonArray.toString(), listType)
        }

        fun parseSingleTransformerResponse(response: String): BotModel {
            val jsonObj = JSONObject(response)
            val transformerType = object : TypeToken<BotModel>() {}.type
            return getGson().fromJson(jsonObj.toString(), transformerType)
        }

        private fun getGson(): Gson {
            return GsonBuilder().create()
        }
    }
}

@Throws(IOException::class)
fun loadMockData(fileName: String, activity: Activity): String {
    val inputStream: InputStream? = activity.javaClass.classLoader?.getResourceAsStream(ROOT_DIRECTORY + fileName)
    return IOUtils.toString(inputStream, "UTF-8")
}

fun hideKeyboard(activity: Activity) {
    val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
}

private const val ROOT_DIRECTORY = "responses/"
const val waitingTime = 1000L