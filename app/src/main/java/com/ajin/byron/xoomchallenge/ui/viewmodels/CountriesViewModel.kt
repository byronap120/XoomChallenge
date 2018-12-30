package com.ajin.byron.xoomchallenge.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ajin.byron.xoomchallenge.data.db.CountryDataBase
import com.ajin.byron.xoomchallenge.data.db.models.Country
import com.ajin.byron.xoomchallenge.repository.CountriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CountriesViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val repository: CountriesRepository

    val countries: LiveData<List<Country>>
        get() = repository.countries


    init {
        val countryDao = CountryDataBase.getDatabase(application).countryDao()
        val disbursementTypeDao = CountryDataBase.getDatabase(application).disbursementTypeDao()
        repository = CountriesRepository(countryDao, disbursementTypeDao)
    }

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

    fun updateCountry(country: Country){
        uiScope.launch {

        }
    }

}