package com.app.byron.xoomcountries.repository.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.app.byron.xoomcountries.data.db.models.Country

data class CountriesResult (
    val data: LiveData<PagedList<Country>>,
    val networkErrors: LiveData<String>
)