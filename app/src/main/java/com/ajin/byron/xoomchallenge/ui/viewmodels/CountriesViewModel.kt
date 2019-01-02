package com.ajin.byron.xoomchallenge.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ajin.byron.xoomchallenge.data.db.models.Country
import com.ajin.byron.xoomchallenge.repository.CountriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CountriesViewModel(application: Application, private val repository: CountriesRepository) :
    AndroidViewModel(application) {

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
                repository.refreshCountries(getApplication())
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