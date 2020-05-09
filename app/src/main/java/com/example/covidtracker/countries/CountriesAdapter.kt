package com.example.covidtracker.countries

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covidtracker.R
import com.example.covidtracker.core.COUNTRY_DATA_EXTRA_KEY
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.countries_details.CountriesDetails
import kotlinx.android.synthetic.main.country_item.view.*

class CountriesAdapter (var items : ArrayList<CountryData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.country_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.countryName.text= item.country
//        holder.countryCases.text= Helper.convertNumber(items.get(position).cases)
//        holder.countryRecoverd.text= Helper.convertNumber(items.get(position).recovered)
//        holder.countryDeaths.text=Helper.convertNumber(items.get(position).deaths)
        holder.countryCases.text= "${item.cases}"
        holder.countryRecoverd.text= "${item.recovered}"
        holder.countryDeaths.text= "${item.deaths}"
        Glide.with(context).load(item.countryInfo.flag).into(holder.countryFlag)
        holder.countryItemId.setOnClickListener{
            val intent = Intent(context,CountriesDetails::class.java)
            intent.putExtra(COUNTRY_DATA_EXTRA_KEY,item)
            context.startActivity(intent)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(countriesData:List<CountryData>) {
        if(countriesData.count()>0){
            items.addAll(countriesData)
            notifyDataSetChanged()
        }

    }
    fun filterList(filterdNames: ArrayList<CountryData>) {
        this.items = filterdNames
        notifyDataSetChanged()
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val countryFlag=view.imageView5
    val countryName=view.countryName
    val countryCases=view.confirmTextViewId
    val countryRecoverd=view.recoveredTextViewId
    val countryDeaths=view.deathTextViewId
    val countryItemId = view.countryItemId
}

