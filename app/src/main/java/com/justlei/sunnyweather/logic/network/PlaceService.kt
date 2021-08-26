package com.justlei.sunnyweather.logic.network

import com.justlei.sunnyweather.SunnyWeatherApplication
import com.justlei.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    /**
     * 搜索城市数据，其中query动态指定，返回对象解析为PlaceResponse
     */
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}