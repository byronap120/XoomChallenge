package com.app.byron.xoomcountries.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.byron.xoomcountries.R
import com.app.byron.xoomcountries.data.db.models.Country
import com.squareup.picasso.Picasso

class CountryAdapter (private val context: Context, private val favoriteClick: (Country) -> Unit) :
    PagedListAdapter<Country, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return ViewHolder(itemView, favoriteClick)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        //viewHolder.bindCountry(countries[position])

        val country = getItem(position)
        if (country != null) {
            (viewHolder as CountryAdapter.ViewHolder).bindCountry(country)
        }
    }

    inner class ViewHolder(itemView: View, private val favoriteClick: (Country) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val countryName: TextView = itemView.findViewById(R.id.country_name)
        private val countryPrefix: TextView = itemView.findViewById(R.id.country_prefix)
        private val countryImage: ImageView = itemView.findViewById(R.id.country_imageView)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.favorite_imageView)

        fun bindCountry(country: Country) {
            with(country) {
                countryName.text = name
                countryPrefix.text = phonePrefix

                val imageUrl = "https://www.countryflags.io/$code/flat/64.png"
                Picasso.get().load(imageUrl).into(countryImage)

                val favoriteImage: Int = if (favorite) R.drawable.ic_star_yellow else R.drawable.ic_star_border
                favoriteImageView.setImageResource(favoriteImage)
                favoriteImageView.setOnClickListener { favoriteClick(this) }
            }
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem.code == newItem.code

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem == newItem
        }
    }
}