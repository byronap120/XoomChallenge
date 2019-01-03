package com.app.byron.xoomcountries.data.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "disbursement_type_table",
    indices = [Index("country")],
    primaryKeys = ["id", "country"],
    foreignKeys = [ForeignKey(
        entity = Country::class,
        parentColumns = ["code"],
        childColumns = ["country"],
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class DisbursementType(
    val id: String,
    val country: String,
    val mode: String,
    val currencyCode: String,
    val disbursementType: String
)