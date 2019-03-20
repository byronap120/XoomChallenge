package com.app.byron.xoomcountries.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.byron.xoomcountries.data.db.models.Country
import com.app.byron.xoomcountries.repository.CountryRepository
import com.app.byron.xoomcountries.repository.models.CountriesResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val countryResult = MutableLiveData<CountriesResult>()

    val countries: LiveData<PagedList<Country>> = Transformations.switchMap(countryResult) { it ->
        it.data
    }

    val networkErrors: LiveData<String> = Transformations.switchMap(countryResult) { it ->
        it.networkErrors
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refreshCountries() {
        countryResult.postValue(repository.getCountries())
    }

    fun updateFavoriteCountry(country: Country) {
        uiScope.launch {
            repository.updateFavoriteCountry(country.code, !country.favorite)
        }
    }

}