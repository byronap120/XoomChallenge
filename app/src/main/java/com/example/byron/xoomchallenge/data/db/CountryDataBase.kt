package com.example.byron.xoomchallenge.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.byron.xoomchallenge.data.db.models.Country
import com.example.byron.xoomchallenge.data.db.models.DisbursementType

@Database(
    entities = [
        Country::class,
        DisbursementType::class],
    version = 1,
    exportSchema = false
)
abstract class CountryDataBase: RoomDatabase() {

    abstract fun countryDao(): CountryDao

    abstract fun disbursementTypeDao(): DisbursementTypeDao


    companion object {
        @Volatile
        private var INSTANCE: CountryDataBase? = null

        fun getDatabase(
            context: Context
        ): CountryDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CountryDataBase::class.java,
                    "country_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}