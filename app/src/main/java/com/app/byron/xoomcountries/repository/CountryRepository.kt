package com.app.byron.xoomcountries.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.app.byron.xoomcountries.data.db.CountryDao
import com.app.byron.xoomcountries.data.db.DisbursementTypeDao
import com.app.byron.xoomcountries.data.db.FavoriteCountryDao
import com.app.byron.xoomcountries.data.db.models.Country
import com.app.byron.xoomcountries.data.db.models.DisbursementType
import com.app.byron.xoomcountries.data.db.models.FavoriteCountry
import com.app.byron.xoomcountries.data.network.CountryService
import com.app.byron.xoomcountries.data.network.models.CountryWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection.HTTP_OK

class CountryRepository(
    private val countryDao: CountryDao,
    private val disbursementTypeDao: DisbursementTypeDao,
    private val favoriteCountryDao: FavoriteCountryDao,
    private val countryService: CountryService
) {

    val countries: LiveData<List<Country>>
        get() = countryDao.getCountries()

    suspend fun refreshCountries() {
        withContext(Dispatchers.IO) {
            try {
                val response = countryService.getCountries(50).await()
                if (response.raw().code() == HTTP_OK) {
                    response.body()?.let {
                        val countryWrapper = response.body() as CountryWrapper
                        val countryList = arrayListOf<Country>()
                        val disbursementTypeList = arrayListOf<DisbursementType>()

                        countryWrapper.items.forEach { country ->
                            country.disbursementOptions?.let {
                                countryList.add(transformCountry(country))
                                disbursementTypeList.addAll(
                                    transformDisbursementType(
                                        country.disbursementOptions,
                                        country.code
                                    )
                                )
                            }
                        }
                        countryDao.insertCountries(countryList)
                        disbursementTypeDao.insertDisbursementTypes(disbursementTypeList)
                    }
                } else {
                    Log.d("error", response.errorBody().toString())
                }
            } catch (error: Error) {
                Log.d("error", error.toString())
            }

        }
    }

    suspend fun updateFavoriteCountry(countryId: String, favorite: Boolean) {
        withContext(Dispatchers.IO) {
            if (favorite) {
                favoriteCountryDao.insertFavoriteCountry(FavoriteCountry(countryId, favorite))
            } else {
                favoriteCountryDao.deleteFavoriteCountry(countryId)
            }
            countryDao.updateFavoriteCountry(countryId, favorite)
        }
    }

    private fun transformCountry(country: com.app.byron.xoomcountries.data.network.models.Country): Country {
        return Country(
            country.code,
            country.name,
            country.residence,
            country.phonePrefix,
            isFavoriteCountry(country.code)
        )
    }

    /**
     * every time that a country is inserted it is compared with local data to check if it's a favorite country
     */
    private fun isFavoriteCountry(countryCode: String): Boolean {
        val favoriteCountry = favoriteCountryDao.getCountryById(countryCode)
        return favoriteCountry?.favorite ?: false
    }

    private fun transformDisbursementType(
        disbursementTypeList: List<com.app.byron.xoomcountries.data.network.models.DisbursementType>,
        countryCode: String
    ): List<DisbursementType> {
        return disbursementTypeList.map {
            DisbursementType(it.id, countryCode, it.mode, it.currency.code, it.disbursementType)
        }
    }
}



