package com.ajin.byron.xoomchallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajin.byron.xoomchallenge.data.db.models.Country
import com.ajin.byron.xoomchallenge.data.db.models.DisbursementType
import com.ajin.byron.xoomchallenge.data.db.models.FavoriteCountry

@Database(
    entities = [
        Country::class,
        DisbursementType::class,
        FavoriteCountry::class],
    version = 1,
    exportSchema = false
)
abstract class CountryDataBase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    abstract fun disbursementTypeDao(): DisbursementTypeDao

    abstract fun favoriteCountryDao(): FavoriteCountryDao

}