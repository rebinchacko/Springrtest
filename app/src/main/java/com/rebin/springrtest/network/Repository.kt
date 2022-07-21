package com.rebin.springrtest.network

object Repository {

    suspend fun getdata(apikey: String) = ApiClient.create().getdata(apikey)

}