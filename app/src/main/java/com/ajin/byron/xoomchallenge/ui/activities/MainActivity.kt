package com.ajin.byron.xoomchallenge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ajin.byron.xoomchallenge.R
import com.ajin.byron.xoomchallenge.databinding.ActivityMainBinding
import com.ajin.byron.xoomchallenge.ui.viewmodels.CountriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}
