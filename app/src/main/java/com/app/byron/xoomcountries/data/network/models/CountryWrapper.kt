package com.app.byron.xoomcountries.data.network.models

import com.google.gson.annotations.SerializedName

data class CountryWrapper(
    @field:SerializedName("total_pages")
    val totalPages: Int,
    val items: List<Country>
)