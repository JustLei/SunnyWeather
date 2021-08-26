package com.justlei.sunnyweather.logic

import androidx.lifecycle.liveData
import com.justlei.sunnyweather.logic.model.Place
import com.justlei.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

/**
 * 仓库层统一封装入口
 */

object Repository {

    /**
     * 城市数据没必要走缓存，每次获取最新的即可
     * LiveData()自动构建并返回Livedata对象，提供挂起函数上下文，指定线程参数，代码块运行在子线程中
     */
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            } else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e : Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result) //无法获取返回的LIVEDATA,使用该方法通知数据变化
    }
}