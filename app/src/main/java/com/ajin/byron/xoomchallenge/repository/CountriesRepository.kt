package com.ajin.byron.xoomchallenge.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ajin.byron.xoomchallenge.data.db.CountryDao
import com.ajin.byron.xoomchallenge.data.db.DisbursementTypeDao
import com.ajin.byron.xoomchallenge.data.db.models.Country
import com.ajin.byron.xoomchallenge.data.db.models.DisbursementType
import com.ajin.byron.xoomchallenge.data.network.CountryService
import com.ajin.byron.xoomchallenge.data.network.models.CountryWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountriesRepository(
    private val countryDao: CountryDao,
    private val disbursementTypeDao: DisbursementTypeDao
) {

    private val TAG: String = "com.byron.log"
    private lateinit var service: CountryService

    val countries: LiveData<List<Country>>
        get() = countryDao.getCountries()

    suspend fun refreshCountries() {
        withContext(Dispatchers.IO) {
            service = CountryService.makeRetrofitService()
            try {
                val response = service.getCountries(50).await()

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
            } catch (error: Error) {
                Log.d(TAG, error.toString())
            }

        }
    }

    private fun transformCountry(country: com.ajin.byron.xoomchallenge.data.network.models.Country): Country {
        return Country(country.code, country.name, country.residence, country.phonePrefix)
    }

    private fun transformDisbursementType(
        disbursementTypeList: List<com.ajin.byron.xoomchallenge.data.network.models.DisbursementType>,
        countryCode: String
    ): List<DisbursementType> {
        return disbursementTypeList.map {
            DisbursementType(it.id, countryCode, it.mode, it.currency.code, it.disbursementType)
        }
    }
}



