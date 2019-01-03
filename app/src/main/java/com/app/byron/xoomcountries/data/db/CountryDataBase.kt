package com.app.byron.xoomcountries.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.byron.xoomcountries.data.db.models.Country
import com.app.byron.xoomcountries.data.db.models.DisbursementType
import com.app.byron.xoomcountries.data.db.models.FavoriteCountry

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