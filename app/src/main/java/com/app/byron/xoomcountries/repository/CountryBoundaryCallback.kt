package com.app.byron.xoomcountries.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.app.byron.xoomcountries.data.db.CountryDao
import com.app.byron.xoomcountries.data.db.DisbursementTypeDao
import com.app.byron.xoomcountries.data.db.FavoriteCountryDao
import com.app.byron.xoomcountries.data.db.models.Country
import com.app.byron.xoomcountries.data.db.models.DisbursementType
import com.app.byron.xoomcountries.data.network.CountryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CountryBoundaryCallback(
    private val countryDao: CountryDao,
    private val disbursementTypeDao: DisbursementTypeDao,
    private val favoriteCountryDao: FavoriteCountryDao,
    private val countryService: CountryService
) : PagedList.BoundaryCallback<Country>() {

    private var isRequestInProgress = false
    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val NETWORK_PAGE_SIZE = 50
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("CountryBoundaryCallback", "onZeroItemsLoaded")
        requestAndSaveData()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Country) {
        Log.d("CountryBoundaryCallback", "onItemAtEndLoaded")
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        uiScope.launch {
            requestCountries(
                lastRequestedPage,
                NETWORK_PAGE_SIZE,
                { countries ->
                    lastRequestedPage++
                    saveCountries(countries)
                },
                { error ->
                    _networkErrors.postValue(error)
                    isRequestInProgress = false
                }
            )
        }
    }


    private suspend fun requestCountries(
        page: Int,
        itemsPerPage: Int,
        onSuccess: (repos: List<com.app.byron.xoomcountries.data.network.models.Country>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        Log.d("CountryService", "page: $page, itemsPerPage: $itemsPerPage")
        try {
            val response = countryService.getCountries(itemsPerPage, page).await()
            if (response.isSuccessful) {
                val countries = response.body()?.items ?: emptyList()
                onSuccess(countries)
            } else {
                onError(response.errorBody()?.string() ?: "unknown error")
            }
        } catch (error: Error) {
            onError(error.message ?: "unknown error")
        }
    }

    private fun saveCountries(countries: List<com.app.byron.xoomcountries.data.network.models.Country>) {
        val countryList = arrayListOf<com.app.byron.xoomcountries.data.db.models.Country>()
        val disbursementTypeList = arrayListOf<DisbursementType>()
        countries.forEach { country ->
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

    private fun transformCountry(country: com.app.byron.xoomcountries.data.network.models.Country): com.app.byron.xoomcountries.data.db.models.Country {
        return com.app.byron.xoomcountries.data.db.models.Country(
            country.code,
            country.name,
            country.residence,
            country.phonePrefix,
            isFavoriteCountry(country.code)
        )
    }

    /**
     * every time that a country is inserted, it is compared with local data to check if it's a favorite country
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