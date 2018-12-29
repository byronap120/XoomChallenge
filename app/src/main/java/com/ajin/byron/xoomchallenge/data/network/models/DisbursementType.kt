package com.ajin.byron.xoomchallenge.data.network.models

import com.google.gson.annotations.SerializedName

data class DisbursementType(
    val id: String,
    val code: String,
    val residence: Boolean,
    @field:SerializedName("disbursement_type")
    val disbursementType: String,
    val currency: Currency
)