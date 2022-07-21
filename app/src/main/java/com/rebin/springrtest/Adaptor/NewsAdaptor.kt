package com.rebin.springrtest.Adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rebin.springrtest.Interface.Clickposition
import com.rebin.springrtest.R
import com.rebin.springrtest.model.Source

class NewsAdaptor(
    var clickListners: Clickposition,
    var responseModel: List<Source>): RecyclerView.Adapter<NewsAdaptor.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvnews:TextView
        var ivnews:ImageView
        var card:LinearLayout
        init {
            card=view.findViewById(R.id.card)
            tvnews=view.findViewById(R.id.tvnews)
            ivnews=view.findViewById(R.id.ivnews)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardnews, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvnews.text=responseModel.get(position).description
        Log.e("hgvsahgdjba",responseModel.get(position).country)
        holder.card.setOnClickListener {
            clickListners.clickposition(responseModel.get(position))
        }
    }

    override fun getItemCount(): Int {
       return responseModel.size
    }

}