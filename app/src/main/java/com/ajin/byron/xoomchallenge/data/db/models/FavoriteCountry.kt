package com.ajin.byron.xoomchallenge.data.db.models

import androidx.room.Entity

@Entity(
    tableName = "favorite_country_table",
    primaryKeys = ["code"]
)
data class FavoriteCountry(
    val code: String,
    val favorite: Boolean
)