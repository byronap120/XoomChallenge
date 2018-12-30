package com.ajin.byron.xoomchallenge.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajin.byron.xoomchallenge.R
import com.ajin.byron.xoomchallenge.databinding.FragmentCountriesBinding
import com.ajin.byron.xoomchallenge.ui.adapters.CountryAdapter
import com.ajin.byron.xoomchallenge.ui.viewmodels.CountriesViewModel


class CountriesFragment : Fragment() {

    private lateinit var countriesViewModel: CountriesViewModel
    private lateinit var adapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCountriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_countries, container, false
        )

        context?.let {
            adapter = CountryAdapter(it)
            binding.countryList.adapter = adapter
            binding.countryList.layoutManager = LinearLayoutManager(it)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Reference viewModel
        countriesViewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)

        // Observe viewModel for changes on posts
        countriesViewModel.countries.observe(this, Observer { value ->
            value?.let {
                adapter.setCountries(value)
                Log.d("com.byron.log", "CAMBIO:" + value.toString())
            }
        })

        countriesViewModel.refreshCountries()
    }
}
