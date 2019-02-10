package com.app.byron.xoomcountries.repository

import androidx.paging.LivePagedListBuilder
import com.app.byron.xoomcountries.data.db.CountryDao
import com.app.byron.xoomcountries.data.db.DisbursementTypeDao
import com.app.byron.xoomcountries.data.db.FavoriteCountryDao
import com.app.byron.xoomcountries.data.db.models.FavoriteCountry
import com.app.byron.xoomcountries.data.network.CountryService
import com.app.byron.xoomcountries.repository.models.CountriesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRepository(
    private val countryDao: CountryDao,
    private val disbursementTypeDao: DisbursementTypeDao,
    private val countryService: CountryService,
    private val favoriteCountryDao: FavoriteCountryDao
) {

    private val DATABASE_PAGE_SIZE = 20

    fun getCountries(): CountriesResult {

        // data source factory from the local cache
        val dataSourceFactory = countryDao.getCountries()
        val boundaryCallback =
            CountryBoundaryCallback(countryDao, disbursementTypeDao, favoriteCountryDao, countryService)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return CountriesResult(data, networkErrors)
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
}



