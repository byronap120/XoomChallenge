package com.app.byron.xoomcountries.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.byron.xoomcountries.R
import com.app.byron.xoomcountries.databinding.FragmentDetailCountryBinding

class DetailCountryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentDetailCountryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail_country, container, false
        )

        val safeArgs: DetailCountryFragmentArgs by navArgs()

        binding.countryNameTextView.text = safeArgs.countryCode

        return binding.root
    }


}
