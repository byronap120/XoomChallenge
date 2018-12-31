package com.ajin.byron.xoomchallenge.data.db

import androidx.room.*
import com.ajin.byron.xoomchallenge.data.db.models.FavoriteCountry

@Dao
interface FavoriteCountryDao {

    @Query("SELECT * from favorite_country_table  WHERE code = :countryId")
    fun getCountryById(countryId: String): FavoriteCountry?

    @Query("DELETE FROM favorite_country_table WHERE code = :countryId")
    fun deleteFavoriteCountry(countryId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteCountry(favoriteCountry: FavoriteCountry)

}