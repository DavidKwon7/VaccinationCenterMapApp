package com.example.vaccinationcentermapapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.example.presentation.vm.SplashState
import com.example.presentation.vm.SplashViewModel
import com.example.vaccinationcentermapapp.R
import com.example.vaccinationcentermapapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i: Int in 1..10) {
            getVaccinationCenter(i)
        }
        observeData()
    }

    private fun getVaccinationCenter(page: Int) {
        splashViewModel.getVaccinationCenter(page)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.getVaccinationCenter.collect { state ->
                    when (state) {
                        is SplashState.Loading -> {

                        }

                        is SplashState.Success -> {
                            binding.pb.setVisibility(View.INVISIBLE)

                            val data = state.data
                            splashViewModel.insertVaccinationCenter(data)

                            if (binding.pb.progress == binding.pb.max) {
                                startDetailFragment()
                            } else {
                                binding.pb.incrementProgressBy(20)
                            }

                        }
                        is SplashState.Failed -> {
                            Timber.e("에러 발생: ${state.message}")
                            state.message.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    fun NavController.navigateSafe(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navExtras: Navigator.Extras? = null
    ) {
        val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)

        if (action != null && currentDestination?.id != action.destinationId) {
            navigate(resId, args, navOptions, navExtras)
        }
    }

    private fun startDetailFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToMapFragment()
        findNavController().navigateSafe(action.actionId, action.arguments)
    }
}