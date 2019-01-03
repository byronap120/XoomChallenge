package com.app.byron.xoomcountries.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.byron.xoomcountries.data.db.models.Country

@Dao
interface CountryDao {

    @Query("SELECT * from country_table ORDER BY favorite DESC")
    fun getCountries(): LiveData<List<Country>>

    @Query("UPDATE country_table SET favorite = :favorite WHERE code = :countryId")
    fun updateFavoriteCountry(countryId: String, favorite: Boolean): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<Country>)
}