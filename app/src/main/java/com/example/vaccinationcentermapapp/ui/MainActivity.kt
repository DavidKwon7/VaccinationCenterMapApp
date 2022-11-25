package com.example.vaccinationcentermapapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.presentation.vm.SplashState
import com.example.presentation.vm.SplashViewModel
import com.example.vaccinationcentermapapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getData()
        observeData()
        for (i: Int in 1..10) {
            getData(i)
        }
    }

    private fun getData(page:Int) {
        splashViewModel.getVaccinationCenter(page)
    }

    private fun observeData() {
        lifecycleScope.launch {
            splashViewModel.getVaccinationCenter.collect {state ->
                when(state) {
                    is SplashState.Loading -> {

                    }
                    is SplashState.Success -> {
                        val data = state.data
                        val tv = findViewById<TextView>(R.id.tv)
                        tv.text = data.toString()
                    }
                    is SplashState.Failed -> {

                    }
                }
            }
        }
    }

}