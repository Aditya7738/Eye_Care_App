package com.example.eyecareapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eyecareapp.api.ApiInterface
import com.example.eyecareapp.model.JSONData

class JSONRepository(private val apiInterface: ApiInterface) {

    private val liveData = MutableLiveData<JSONData>()

    val data: LiveData<JSONData>
        get() = liveData

    suspend fun getDataFromJSONRepository(){
        val result = apiInterface.getData()

        if(result.body() != null){
            liveData.postValue(result.body())
        }
    }
}