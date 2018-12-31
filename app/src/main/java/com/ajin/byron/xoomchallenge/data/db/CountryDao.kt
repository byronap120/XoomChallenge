package com.ajin.byron.xoomchallenge.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ajin.byron.xoomchallenge.data.db.models.Country

@Dao
interface CountryDao {

    @Query("SELECT * from country_table ORDER BY favorite DESC")
    fun getCountries(): LiveData<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<Country>)

    @Update
    fun updateCountry(country: Country)

    @Query("UPDATE country_table SET favorite = :favorite WHERE code = :countryId")
    fun updateFavoriteCountry(countryId: String, favorite: Boolean): Int
}