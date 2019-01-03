package com.app.byron.xoomcountries.data.network

import com.app.byron.xoomcountries.data.network.models.CountryWrapper
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryService {

    @GET("/catalog/v2/countries")
    fun getCountries(@Query("page_size") pageSize: Int): Deferred<Response<CountryWrapper>>

}