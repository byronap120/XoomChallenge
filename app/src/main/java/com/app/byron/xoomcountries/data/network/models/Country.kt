package com.app.byron.xoomcountries.data.network.models

import com.google.gson.annotations.SerializedName

data class Country(
    val code: String,
    val name: String,
    val residence: Boolean,
    @field:SerializedName("phone_prefix")
    val phonePrefix: String,
    @field:SerializedName("disbursement_options")
    val disbursementOptions: List<DisbursementType>?
)