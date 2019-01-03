package com.app.byron.xoomcountries.data.db.models

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "country_table",
    indices = [Index("code")],
    primaryKeys = ["code"]
)
data class Country(
    val code: String,
    val name: String,
    val residence: Boolean,
    val phonePrefix: String,
    val favorite: Boolean = false
)