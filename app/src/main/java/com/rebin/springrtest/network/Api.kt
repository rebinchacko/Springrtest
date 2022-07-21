package com.rebin.springrtest.network

import com.rebin.springrtest.model.ResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("v2/sources?")
    suspend fun getdata(@Query(value = "apikey", encoded = true)apikey: String):
            ResponseModel
}