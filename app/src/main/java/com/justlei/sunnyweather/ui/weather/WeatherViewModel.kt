package com.justlei.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.justlei.sunnyweather.logic.Repository
import com.justlei.sunnyweather.logic.model.Location

class WeatherViewModel : ViewModel(){

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData){
        Repository.refreshWeather(it.lng,it.lat)
    }

    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value = Location(lng,lat)
    }
}