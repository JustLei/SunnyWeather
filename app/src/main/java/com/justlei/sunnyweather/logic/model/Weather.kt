package com.justlei.sunnyweather.logic.model

/**
 * 用于封装Realtime与Daily
 */
data class Weather(val realtime: RealtimeResponse.Realtime,val daily: DailyResponse.Daily)
