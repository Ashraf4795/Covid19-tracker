package com.example.covidtracker.global

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covidtracker.R
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.utils.Helper
import kotlinx.android.synthetic.main.top_countries_layout.view.*

class TopCountriesAdapterAdapter(val items : ArrayList<CountryData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.top_countries_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.countryName.text=items.get(position).country
        holder.countryCases.text= Helper.convertNumber(items.get(position).cases)
        Glide.with(context).load(items.get(position).countryInfo.flag).into(holder.countryFlag)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val countryFlag=view.imageView5
    val countryName=view.countryName
    val countryCases=view.countryCases
}
