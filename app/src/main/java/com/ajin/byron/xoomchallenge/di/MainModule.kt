package com.ajin.byron.xoomchallenge.di

import com.ajin.byron.xoomchallenge.ui.viewmodels.CountriesViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mainModule = module {

    viewModel { CountriesViewModel(get()) }
}