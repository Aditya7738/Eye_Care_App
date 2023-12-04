package com.example.eyecareapp.api

import com.example.eyecareapp.model.JSONData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("productdetails/6701/253620?lang=en&store=KWD")
    suspend fun getData(): Response<JSONData>
}