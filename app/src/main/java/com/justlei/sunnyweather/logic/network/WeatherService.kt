package com.justlei.sunnyweather.logic.network

import com.justlei.sunnyweather.SunnyWeatherApplication
import com.justlei.sunnyweather.logic.model.DailyResponse
import com.justlei.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 天气接口
 */
interface WeatherService {

    /**
     * 获取实时天气
     * @param lng:经度
     * @param lat:纬度
     * @return RealtimeResponse
     */
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng:String,@Path("lat")lat:String)
            :Call<RealtimeResponse>

    /**
     * 获取未来的天气信息
     * @param lng:经度
     * @param lat:纬度
     * @return RealtimeResponse
     */
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng:String,@Path("lat")lat:String)
            :Call<DailyResponse>

}