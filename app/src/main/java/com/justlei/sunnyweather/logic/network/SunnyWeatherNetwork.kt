package com.justlei.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 统一的数据源访问入口，对所有网络请求的API进行封装
 */
object SunnyWeatherNetwork {
    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query:String) = placeService.searchPlaces(query).await()
    suspend fun getDailyWeather(lng:String,lat:String) = weatherService.getDailyWeather(lng, lat).await()
    suspend fun getRealtimeWeather(lng: String,lat: String) = weatherService.getRealtimeWeather(lng, lat).await()


    //利用suspendCoroutine，简化回调写法
    //定义await为Call<T>的扩展函数，所有返回值为Call的请求都可以使用该方法
    private suspend fun <T> Call<T>.await(): T {
        //立即将当前协程挂起，在一个普通线程中执行lambda
        return suspendCoroutine { continuation ->
            //发起网络请求
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)  //协程恢复执行
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}