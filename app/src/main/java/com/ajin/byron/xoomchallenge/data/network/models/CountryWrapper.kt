package com.ajin.byron.xoomchallenge.data.network.models

import com.google.gson.annotations.SerializedName

data class CountryWrapper(
    @field:SerializedName("total_pages")
    val totalPages: Int,
    val items: List<Country>
)