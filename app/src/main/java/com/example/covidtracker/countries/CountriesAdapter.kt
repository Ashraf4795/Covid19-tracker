package com.example.covidtracker.countries

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covidtracker.R
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.utils.Helper
import kotlinx.android.synthetic.main.country_item.view.*

class CountriesAdapter (val items : ArrayList<CountryData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.country_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.countryName.text= items[position].country
//        holder.countryCases.text= Helper.convertNumber(items.get(position).cases)
//        holder.countryRecoverd.text= Helper.convertNumber(items.get(position).recovered)
//        holder.countryDeaths.text=Helper.convertNumber(items.get(position).deaths)
        holder.countryCases.text= "${items.get(position).cases}"
        holder.countryRecoverd.text= "${items.get(position).recovered}"
        holder.countryDeaths.text= "${items.get(position).deaths}"
        Glide.with(context).load(items[position].countryInfo.flag).into(holder.countryFlag)

    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val countryFlag=view.imageView5
    val countryName=view.countryName
    val countryCases=view.confirmTextViewId
    val countryRecoverd=view.recoveredTextViewId
    val countryDeaths=view.deathTextViewId
}
