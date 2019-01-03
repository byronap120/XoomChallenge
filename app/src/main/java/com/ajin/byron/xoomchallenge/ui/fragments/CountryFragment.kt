package com.ajin.byron.xoomchallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajin.byron.xoomchallenge.R
import com.ajin.byron.xoomchallenge.data.db.models.Country
import com.ajin.byron.xoomchallenge.databinding.FragmentCountriesBinding
import com.ajin.byron.xoomchallenge.ui.adapters.CountryAdapter
import com.ajin.byron.xoomchallenge.ui.viewmodels.CountryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryFragment : Fragment() {

    private val viewModel by viewModel<CountryViewModel>()
    private lateinit var adapter: CountryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentCountriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_countries, container, false
        )

        context?.let {
            adapter = CountryAdapter(it, ::favoriteClick)
            binding.countryList.adapter = adapter
            binding.countryList.layoutManager = LinearLayoutManager(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.countries.observe(this, Observer { value ->
            value?.let {
                adapter.setCountries(value)
            }
        })
        viewModel.refreshCountries()
    }

    private fun favoriteClick(country: Country) {
        viewModel.updateFavoriteCountry(country)
    }
}
