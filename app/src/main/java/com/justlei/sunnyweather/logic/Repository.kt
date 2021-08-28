package com.justlei.sunnyweather.logic

import androidx.lifecycle.liveData
import com.justlei.sunnyweather.logic.dao.PlaceDao
import com.justlei.sunnyweather.logic.model.Place
import com.justlei.sunnyweather.logic.model.Weather
import com.justlei.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * 仓库层统一封装入口
 */

object Repository {

    /**
     * 城市数据没必要走缓存，每次获取最新的即可
     * LiveData()自动构建并返回Livedata对象，提供挂起函数上下文，指定线程参数，代码块运行在子线程中
     */
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        } else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    /**
     * 一次性获取实时天气与未来天气，封装到Weather中
     */
    fun refreshWeather(lng:String,lat:String) = fire(Dispatchers.IO) {
        //构建协程作用域，使用async并行请求数据，并保证请求都成功响应才执行下一步
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}" +
                        "daily response status is ${dailyResponse.status}"))
            }
        }
    }

    /**
     * 统一try catch，简化代码
     * 按照liveData()函数的参数接受标准定义的高阶函数
     */
    private fun <T> fire(context:CoroutineContext,block:suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e:Exception){
                Result.failure<T>(e)
            }
            emit(result) //无法获取返回的LIVEDATA,使用该方法通知数据变化
        }


    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}