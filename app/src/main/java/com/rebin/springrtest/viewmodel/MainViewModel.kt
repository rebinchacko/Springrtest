package com.rebin.springrtest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.rebin.springrtest.model.ResponseModel
import com.rebin.springrtest.network.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel:ViewModel() {
    var mresponse= MutableLiveData<ResponseModel>()
    var merror=MutableLiveData<String>()

    var gson=Gson()
    fun getdata() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val call = Repository.getdata("d29d58aab88d4ea0b04ddb245a230068")
                if (call != null) {
                    call.apply {
                        mresponse.postValue(this)
                    }
                } else {
                    merror.postValue("Something went wrong")
                    throw Exception("Something went wrong")
                }
            }catch (e:Exception){
                Log.e("jkdkaskj",e.message.toString())

                merror.postValue(e.message)
            }
        }
    }

}