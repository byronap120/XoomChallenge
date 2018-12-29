package com.ajin.byron.xoomchallenge.data.network

import com.ajin.byron.xoomchallenge.data.network.models.Country
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CountryService {

    @GET("/posts")
    fun getCountries(): Deferred<Response<List<Country>>>

    companion object {
        private const val BASE_URL = "https://mobile.xoom.com/catalog/v2/countries?page_size=50"

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