package com.justlei.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * retrofit构建器，创建Service接口的动态代理
 */
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())  //指定JSON解析工具
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //类型实化,免去传Class的烦恼
    inline fun <reified T> create():T = create(T::class.java)
}