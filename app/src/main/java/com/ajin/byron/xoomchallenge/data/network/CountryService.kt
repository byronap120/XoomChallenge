package com.ajin.byron.xoomchallenge.data.network

import com.ajin.byron.xoomchallenge.data.network.models.CountryWrapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

interface CountryService {

    @GET("/catalog/v2/countries")
    fun getCountries(@Query("page_size") pageSize: Int): Deferred<Response<CountryWrapper>>

    companion object {
        private const val BASE_URL = "https://mobile.xoom.com"

        fun makeRetrofitService(): CountryService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(CountryService::class.java)
        }
    }
}