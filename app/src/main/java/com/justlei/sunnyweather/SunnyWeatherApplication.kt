package com.justlei.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * MVVM架构，ViewModel不再持有Activity，容易出现缺少CONTEXT的情况
 * 故使用此类来保存全局唯一的APPLICATION
 */
class SunnyWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")  //去除内存泄露警告
        lateinit var context : Context

        const val TOKEN = "InEvNLnueKhQsXMD"     //彩云天气Token
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


}