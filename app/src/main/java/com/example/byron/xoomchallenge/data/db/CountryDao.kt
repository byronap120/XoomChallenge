package com.example.byron.xoomchallenge.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.byron.xoomchallenge.data.db.models.Country

@Dao
interface CountryDao {

    @Query("SELECT * from country_table")
    fun getCountries(): LiveData<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(post: List<Country>)
}