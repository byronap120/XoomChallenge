package com.app.byron.xoomcountries.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.byron.xoomcountries.R
import com.app.byron.xoomcountries.data.db.models.Country
import com.app.byron.xoomcountries.databinding.FragmentCountriesBinding
import com.app.byron.xoomcountries.ui.adapters.CountryAdapter
import com.app.byron.xoomcountries.ui.viewmodels.CountryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryFragment : Fragment() {

    private val viewModel by viewModel<CountryViewModel>()
    private lateinit var adapter: CountryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentCountriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_countries, container, false
        )

        context?.let {
            adapter = CountryAdapter(::favoriteClick)
            binding.countryList.adapter = adapter
            binding.countryList.layoutManager = LinearLayoutManager(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.countries.observe(this, Observer { value ->
            value?.let {
                Log.d("byron.countries", "countries list$value")
                adapter.submitList(value)
            }
        })

        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(context, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })

        viewModel.refreshCountries()
    }

    private fun favoriteClick(country: Country) {
        viewModel.updateFavoriteCountry(country)
    }
}
