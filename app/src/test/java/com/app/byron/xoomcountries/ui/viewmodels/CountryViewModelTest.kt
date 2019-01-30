package com.app.byron.xoomcountries.ui.viewmodels

import com.app.byron.xoomcountries.repository.CountryRepository
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import androidx.arch.core.executor.testing.InstantTaskExecutorRule


@RunWith(JUnit4::class)
class CountryViewModelTest {


    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private val repository = Mockito.mock(CountryRepository::class.java)
    private lateinit var viewModel: CountryViewModel


    @Before
    fun init() {
        // need ot init after instant executor rule is established.
        viewModel = CountryViewModel(repository)
    }
}

