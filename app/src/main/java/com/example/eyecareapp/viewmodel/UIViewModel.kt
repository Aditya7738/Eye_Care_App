package com.example.eyecareapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eyecareapp.model.JSONData
import com.example.eyecareapp.repository.JSONRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UIViewModel(private val repository: JSONRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getDataFromJSONRepository()
        }

    }

    val data: LiveData<JSONData>
        get() = repository.data



    fun getImageHeight(screenHeight: Int): Int{
        return screenHeight/2
    }


}