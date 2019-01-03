package com.app.byron.xoomcountries.di

import android.app.Application
import androidx.room.Room
import com.app.byron.xoomcountries.data.db.CountryDataBase
import com.app.byron.xoomcountries.data.network.CountryService
import com.app.byron.xoomcountries.repository.CountryRepository
import com.app.byron.xoomcountries.ui.viewmodels.CountryViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {

    single { Room.databaseBuilder(get(), CountryDataBase::class.java, "country_database").build() }

    single { get<CountryDataBase>().countryDao() }

    single { get<CountryDataBase>().disbursementTypeDao() }

    single { get<CountryDataBase>().favoriteCountryDao() }

    single { CountryRepository(get(), get(), get(), get()) }

    viewModel { CountryViewModel(get()) }

    single { provideOkHttp(get()) }

    single { provideRetrofit(get()) }

}

fun provideOkHttp(application: Application): OkHttpClient {
    val cacheSize = (5 * 1024 * 1024).toLong()
    val cache = Cache(application.filesDir, cacheSize)
    return OkHttpClient.Builder()
        .cache(cache)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): CountryService {
    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://mobile.xoom.com")
        .build()
    return retrofit.create(CountryService::class.java)
}