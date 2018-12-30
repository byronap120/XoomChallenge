package com.ajin.byron.xoomchallenge.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ajin.byron.xoomchallenge.R
import com.ajin.byron.xoomchallenge.data.db.models.Country
import com.squareup.picasso.Picasso

class CountryAdapter internal constructor(val context: Context) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private var countries = emptyList<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val country = countries[position]
        viewHolder.countryName.text = country.name
        viewHolder.countryPrefix.text = country.phonePrefix
        val imageUrl = "https://www.countryflags.io/${country.code}/flat/64.png"
        viewHolder.updateWithUrl(imageUrl)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun setCountries(countries: List<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.country_name)
        val countryPrefix: TextView = itemView.findViewById(R.id.country_prefix)
        val countryImage: ImageView = itemView.findViewById(R.id.country_imageView)

        fun updateWithUrl(url: String) {
            Picasso.get().load(url).into(countryImage)
        }
    }
}