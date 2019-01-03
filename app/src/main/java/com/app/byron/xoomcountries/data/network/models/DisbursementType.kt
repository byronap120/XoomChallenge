package com.app.byron.xoomcountries.data.network.models

import com.google.gson.annotations.SerializedName

data class DisbursementType(
    val id: String,
    val mode: String,
    @field:SerializedName("disbursement_type")
    val disbursementType: String,
    val currency: Currency
)