package com.example.vaccinationcentermapapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.presentation.model.VaccinationCenterUiModel
import com.example.presentation.vm.SplashState
import com.example.presentation.vm.SplashViewModel
import com.example.vaccinationcentermapapp.R
import com.example.vaccinationcentermapapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.timer

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i: Int in 1..10) {
            getVaccinationCenter(i)
        }

        showMain()
        observeData()
        moveToMap()

    }



    private fun getVaccinationCenter(page:Int) {
        splashViewModel.getVaccinationCenter(page)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.getVaccinationCenter.collect {state ->
                    when(state) {
                        is SplashState.Loading -> {

                        }
                        is SplashState.Success -> {
                            val data = state.data
                            binding.tv.text = data.toString()
                            splashViewModel.insertVaccinationCenter(data)
                            val k = splashViewModel.insertVaccinationCenter(data)
                            Log.d("TAG", "observeData: $k")

                            if (k.isActive) {

                            }
                        }
                        is SplashState.Failed -> {

                        }
                    }
                }
            }
        }
    }

    private fun moveToMap() {
        binding.btn.setOnClickListener {
            //splashViewModel.insertVaccinationCenter()
            val navController = Navigation.findNavController(it)
            navController.navigate(
                R.id.action_splashFragment_to_mapFragment
            )
        }
    }

    private fun showMain() {
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_mapFragment)
        }, 2000L)
    }
}