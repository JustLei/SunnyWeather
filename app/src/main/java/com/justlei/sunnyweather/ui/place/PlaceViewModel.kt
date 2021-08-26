package com.justlei.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.justlei.sunnyweather.logic.Repository
import com.justlei.sunnyweather.logic.model.Place

class PlaceViewModel : ViewModel() {

    private  val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()  //缓存，防止屏幕旋转时数据丢失

    /**
     * 可观察的LIVEDATA对象
     */
    val placeLiveData = Transformations.switchMap(searchLiveData) {query ->
        Repository.searchPlaces(query)
    }

    /**
     * 调用该方法触发switchMap以获取城市数据
     */
    fun searchPlaces(query: String){
        searchLiveData.value = query
    }
}