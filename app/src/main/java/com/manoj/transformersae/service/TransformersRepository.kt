package com.manoj.transformersae.service

import com.manoj.transformersae.BuildConfig.PATH_TRANSFOMERS
import com.manoj.transformersae.R
import com.manoj.transformersae.base.ResourceState
import com.manoj.transformersae.custom.error.CustomThrowable
import com.manoj.transformersae.model.BotDao
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.getStringResource
import com.manoj.transformersae.util.AppUtill.verifyAvailableNetwork
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Callable

/**
 * Created by Manoj Vemuru on 2020-06-16.
 * manojvemuru@gmail.com
 */
class TransformersRepository(private val restService: RestService) {

    fun getToken(): Observable<ResourceState> {
        val handler = RequestHandler<String>()
        if (checkInternetConnection(handler))
            restService.getRequest("allspark").enqueue(handler.callback)
        return handler.publishSubject
    }

    fun storeTransformer(botModel: BotModel, id: String): Observable<ResourceState> {
        val handler = RequestHandler<BotModel>()
        if (checkInternetConnection(handler))
            restService.postRequest(PATH_TRANSFOMERS, id, botModel).enqueue(handler.callback)
        return handler.publishSubject
    }

    fun updateTransformer(botModel: BotModel, id: String): Observable<ResourceState> {
        val handler = RequestHandler<BotModel>()
        if (checkInternetConnection(handler))
            restService.putRequest(PATH_TRANSFOMERS, id, botModel).enqueue(handler.callback)
        return handler.publishSubject
    }

    fun deleteTransformer(botModel: BotModel, id: String): Observable<ResourceState> {
        val handler = RequestHandler<Void>()
        if (checkInternetConnection(handler))
            restService.deleteRequest("$PATH_TRANSFOMERS/${botModel.id}", id).enqueue(handler.callback)
        return handler.publishSubject
    }

    fun removeBotModel(state: ResourceState, botModel: BotModel, botDao: BotDao): Observable<ResourceState>{
        return Observable.fromCallable {
            when(state) {
                is ResourceState.Success<*> -> {
                    botDao.deleteBot(botModel)
                    ResourceState.Success(Any())
                }
                else -> state
            }
        }
    }

    fun loadBotList(botDao: BotDao): Flowable<List<BotModel>> {
        return botDao.getAll()
    }

    fun launchWar(list: List<BotModel>): Observable<ResourceState> {
        return Observable.create { emitter ->
            val autoBotList: ArrayList<BotModel> = ArrayList()
            val decepticonBotList: ArrayList<BotModel> = ArrayList()

            for (botModel: BotModel in list) {
                if (botModel.team == AppUtill.TEAM_A_KEY)
                    autoBotList.add(botModel)
                else
                    decepticonBotList.add(botModel)
            }

            autoBotList.sort()
            decepticonBotList.sort()
            var countAutoBotWinner: Int = 0;
            var countDecipticon: Int = 0;
            for (autoBot: BotModel in autoBotList) {
                if (autoBot.name.contains("Optimus Prime") || autoBot.name.contains("Predaking")) {
                    countAutoBotWinner++
                }
            }
            for (decepticon: BotModel in decepticonBotList) {
                if (decepticon.name.contains("Optimus Prime") || decepticon.name.contains("Predaking")) {
                    countDecipticon++
                }
            }

            when {
                (countAutoBotWinner + countDecipticon) > 1 -> {
                    emitter.onNext(ResourceState.Success("all competitors destroyed"))
                }
                countAutoBotWinner > 0 -> {
                    emitter.onNext(ResourceState.Success("Winning Team (Autobots)"))
                }
                countDecipticon > 0 -> {
                    emitter.onNext(ResourceState.Success("Winning Team (Decipticons)"))
                }
                else -> {
                    emitter.onNext(ResourceState.Success(processWar(autoBotList, decepticonBotList)))
                }
            }
        }
    }

    private fun processWar(listAutoBot: ArrayList<BotModel>, listDecipticons: ArrayList<BotModel>): String {
        var autoBotCount: ArrayList<String> = ArrayList()
        var decipticonCount: ArrayList<String> = ArrayList()

        var count: Int = when (listAutoBot.size >= listDecipticons.size) {
            true -> listDecipticons.size - 1
            false -> listAutoBot.size - 1
        }
        for (i in 0..count) {
            //check for courage and strength difference
            if (listAutoBot[i].courage > listDecipticons[i].courage
                    && listAutoBot[i].strength > listDecipticons[i].strength
                    && checkCourageAndStrength(listAutoBot[i], listDecipticons[i])) {
                autoBotCount.add(listAutoBot[i].name)
            } else if (listDecipticons[i].courage > listAutoBot[i].courage
                    && listDecipticons[i].strength > listAutoBot[i].strength
                    && checkCourageAndStrength(listDecipticons[i], listAutoBot[i])) {
                decipticonCount.add(listDecipticons[i].name)
            } else if (listAutoBot[i].skill - listDecipticons[i].skill >= 3) {
                autoBotCount.add(listAutoBot[i].name)
            } else if (listDecipticons[i].skill - listAutoBot[i].skill >= 3) {
                decipticonCount.add(listDecipticons[i].name)
            } else if (listAutoBot[i].overAllRating > listDecipticons[i].overAllRating) {
                autoBotCount.add(listAutoBot[i].name)
            } else if (listDecipticons[i].overAllRating > listAutoBot[i].overAllRating) {
                decipticonCount.add(listDecipticons[i].name)
            }
        }

        var winningTeam = ""
        winningTeam = when (count > 0) {
            true -> """${count + 1} Battles"""
            false -> """${count + 1} Battle"""
        }
        winningTeam += "\n"
        if (autoBotCount.size > decipticonCount.size) {
            //TODO: autobots won
            winningTeam += "Winning Team (Autobots): " + getWinners(autoBotCount) + "\n"
            if (listDecipticons.size > listAutoBot.size) {
                winningTeam += "Survivors from the losing team (Decepticons): " + getSurvivorsNames(listDecipticons, listAutoBot.size) + "\n"
            }

        } else if (decipticonCount.size > autoBotCount.size) {
            //TODO: Decipticons won
            winningTeam = winningTeam + "Winning Team (Decepticons): " + getWinners(decipticonCount) + "\n"
            if (listAutoBot.size > listDecipticons.size) {
                winningTeam = winningTeam + "Survivors from the losing team (Autobots): " + getSurvivorsNames(listAutoBot, listDecipticons.size) + "\n"
            }
        } else {
            //TODO: all transformers in battle destroyed
            winningTeam = "all transformers in battle destroyed"
            return winningTeam
        }

        return winningTeam
    }

    private fun checkCourageAndStrength(botModel1: BotModel, botModel: BotModel): Boolean {
        return when (botModel1.courage - botModel.courage >= 4 && botModel1.strength - botModel.strength >= 3) {
            true -> true
            false -> false
        }
    }

    private fun getWinners(listNames: ArrayList<String>): String {
        var winnersNames = listNames[0]

        if (listNames.size > 1) {
            for (i in 1 until listNames.size) {
                val name = listNames[i]
                winnersNames = ", $name"
            }
        }
        return winnersNames
    }

    private fun getSurvivorsNames(listAutoBot: ArrayList<BotModel>, startPos: Int): String {
        var survivorsNames = listAutoBot[startPos].name

        if (listAutoBot.size > startPos + 1) {
            for (i in startPos + 1 until listAutoBot.size) {
                val name = listAutoBot[i].name
                survivorsNames = ", $name"
            }
        }

        return survivorsNames
    }

    private fun checkInternetConnection(handler: RequestHandler<*>): Boolean {
        return when (verifyAvailableNetwork()) {
            true -> {
                true
            }
            false -> {
                handler.noInternet()
                return false
            }
        }
    }

    inner class RequestHandler<T>(val needResponse: Boolean = false) {
        val publishSubject: PublishSubject<ResourceState> = PublishSubject.create()

        val callback = object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                publishSubject.onNext(ResourceState.Failure(t))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                when (response.isSuccessful) {
                    true -> publishSubject.onNext(
                            ResourceState.Success(
                                    when (needResponse) {
                                        false -> response.body()
                                        true -> response
                                    }
                            )
                    )
                    else -> publishSubject.onNext(
                            ResourceState.ServerError(
                                    CustomThrowable(
                                            response.errorBody()?.string(),
                                            response.code()
                                    )
                            )
                    )
                }
            }
        }

        fun noInternet() {
            publishSubject.onError(Throwable(getStringResource(R.string.no_internet)))
        }
    }
}