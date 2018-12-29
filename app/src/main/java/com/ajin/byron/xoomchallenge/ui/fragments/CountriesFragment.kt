package com.ajin.byron.xoomchallenge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ajin.byron.xoomchallenge.R
import com.ajin.byron.xoomchallenge.databinding.FragmentCountriesBinding


class CountriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCountriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_countries, container, false)
        return binding.root
    }

}
