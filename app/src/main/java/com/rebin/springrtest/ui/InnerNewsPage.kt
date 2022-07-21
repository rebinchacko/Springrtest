package com.rebin.springrtest.ui

import android.Manifest
import android.app.SearchManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rebin.springrtest.R
import com.rebin.springrtest.model.Source
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class InnerNewsPage : AppCompatActivity() {
    lateinit var ivnews:ImageView
    lateinit var tvnewshead:TextView
    lateinit var tvauthor:TextView
    lateinit var tvshare:ImageView
    lateinit var tvdate:TextView
    lateinit var tvnewsdesc:TextView
    lateinit var btsaveimg:Button
    lateinit var msg:String
    lateinit var url:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_news_page)
        ActivityCompat.requestPermissions(
                this, arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
        ),
        1)

        initvalues()
        clicklistners()

    }

    private fun clicklistners() {
        tvshare.setOnClickListener{
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val fb = view.findViewById<LinearLayout>(R.id.lytfacebook)
            val insta = view.findViewById<LinearLayout>(R.id.lytinstagram)
            val whatsapp = view.findViewById<LinearLayout>(R.id.lytwhtsapp)
            val google = view.findViewById<LinearLayout>(R.id.lytgoogle)
            fb.setOnClickListener {
                dialog.dismiss()
                onclick("fb")
            }
            insta.setOnClickListener {
                dialog.dismiss()
                onclick("insta")
            }
            whatsapp.setOnClickListener {
                dialog.dismiss()
                onclick("whatsapp")
            }
            google.setOnClickListener {
                dialog.dismiss()
                onclickgoogle()
            }

            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }

        btsaveimg.setOnClickListener {
            saveimage()
        }
    }

    private fun saveimage() {
        var fileOutputStream=null
        var file=getDisc()
        if(!file.exists()&&file.mkdir()){
            file.mkdirs()
        }
        var simpledateformat=SimpleDateFormat("yyyymmhhmm")
        var date=simpledateformat.format(Date())
        var name="IMG"+date+".jpg"
        var filename=file.absolutePath+"/"+name
        var newfile=File(filename)

        try {
            var draw=ivnews.drawable as BitmapDrawable
            var bitmap=draw.bitmap
            var fileoutput=FileOutputStream(newfile)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileoutput)
            fileoutput.flush()
            fileoutput.close()
        }catch(e:Exception){
            e.printStackTrace()
        }
       refreshgallery(newfile)
    }

    private fun refreshgallery(newfile: File) {
        var intent=Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.setData(Uri.fromFile(newfile))
        this.sendBroadcast(intent)
        Toast.makeText(this,"Image save succesfully to your device",Toast.LENGTH_SHORT).show()
    }

    private fun getDisc(): File {

        var file=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File(file,"Firebasetutoriol")
    }

    private fun onclickgoogle() {
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY,url )
        startActivity(intent)
    }

    private fun onclick(s: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent
            .putExtra(
                Intent.EXTRA_TEXT,
                msg
            )
        sendIntent.type = "text/plain"
        if(s=="fb") {
            sendIntent.setPackage("com.facebook.orca")
        }else if(s=="insta"){
            sendIntent.setPackage("com.instagram.android")
        }else if(s=="whatsapp"){
            sendIntent.setPackage("com.whatsapp")
        }
        try {
            startActivity(sendIntent)
        } catch (ex: Exception) {
            if(s=="fb") {
                Toast.makeText(this, "Please Install Facebook Messenger", Toast.LENGTH_LONG).show()
            }else if(s=="insta"){
                Toast.makeText(this, "Please Install Instagram", Toast.LENGTH_LONG).show()
            }else if(s=="whatsapp"){
                Toast.makeText(this, "Please Install Instagram", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initvalues() {
        ivnews=findViewById(R.id.ivnews)
        tvnewshead=findViewById(R.id.tvnewshead)
        tvauthor=findViewById(R.id.tveditor)
        tvshare=findViewById(R.id.ivshare)
        tvdate=findViewById(R.id.tvdate)
        tvnewsdesc=findViewById(R.id.tvnewsdescription)
        btsaveimg=findViewById(R.id.btsave)

        if(intent.getSerializableExtra("news")!=null){
            val news=intent.getSerializableExtra("news") as Source
            tvnewshead.text=news.name
            msg=news.name+"/n"+news.description
            url=news.url
//            tvauthor.text=news.
//            tvdate.text=
            tvnewsdesc.text=news.description
        }

    }
}