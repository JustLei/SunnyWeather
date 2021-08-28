package com.justlei.sunnyweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.justlei.sunnyweather.SunnyWeatherApplication
import com.justlei.sunnyweather.logic.model.Place

/**
 * 存储城市名在本地
 */
object PlaceDao {
    private fun sharedPreferences() = SunnyWeatherApplication.context.
        getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

    fun savePlace(place:Place){
        sharedPreferences().edit {
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavedPlace():Place {
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")
}