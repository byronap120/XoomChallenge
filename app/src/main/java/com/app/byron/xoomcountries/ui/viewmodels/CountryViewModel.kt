package com.app.byron.xoomcountries.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.byron.xoomcountries.data.db.models.Country
import com.app.byron.xoomcountries.repository.CountryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val countries: LiveData<List<Country>>
        get() = repository.countries

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refreshCountries() {
        uiScope.launch {
            try {
                repository.refreshCountries()
            } catch (error: Error) {
                Log.d("com.byron.test", error.toString())
            } finally {

            }
        }
    }

    fun updateFavoriteCountry(country: Country) {
        uiScope.launch {
            repository.updateFavoriteCountry(country.code, !country.favorite)
        }
    }

}