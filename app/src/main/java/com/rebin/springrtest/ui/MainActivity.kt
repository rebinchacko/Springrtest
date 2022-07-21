package com.rebin.springrtest.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.rebin.springrtest.Adaptor.NewsAdaptor
import com.rebin.springrtest.Interface.Clickposition
import com.rebin.springrtest.R
import com.rebin.springrtest.model.Source
import com.rebin.springrtest.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() ,Clickposition {

    lateinit var viewmodel:MainViewModel
    lateinit var newsadaptor:NewsAdaptor
    lateinit var rv1:RecyclerView
    lateinit var clicklistner:Clickposition
    lateinit var progressDialog :ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Application is loading, please wait")
        progressDialog.show()
        rv1=findViewById(R.id.rv1)
        clicklistner=this
        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewmodel.getdata()

        obsserver()

    }

    private fun obsserver() {
        viewmodel.mresponse.observe(this, Observer {

            progressDialog.dismiss()
            if(it.status=="ok") {
                newsadaptor = NewsAdaptor(clicklistner,it.sources)
                rv1.adapter = newsadaptor

            }
        })
        viewmodel.merror.observe(this, Observer {
            progressDialog.dismiss()
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        })

    }

    override fun clickposition(get: Source) {
        val intent=Intent(this,InnerNewsPage::class.java)
        intent.putExtra("news",get)
        startActivity(intent)
    }
}