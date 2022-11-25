package com.example.vaccinationcentermapapp.ui.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.presentation.vm.MapState
import com.example.presentation.vm.MapViewModel
import com.example.vaccinationcentermapapp.R
import com.example.vaccinationcentermapapp.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment() {

    lateinit var binding: FragmentMapBinding
    private val mapViewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllVaccinationCenter()
        observeData()
    }

    private fun getAllVaccinationCenter() {
        mapViewModel.getAllVaccinationCenter()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mapViewModel.getVaccinationCenter.collect {state ->
                    when(state) {
                        is MapState.Loading -> {

                        }
                        is MapState.Success -> {
                            state.data.forEach {
                                binding.vaccinationCenterModel = it
                            }
                            Log.d("Map Test", "observeData: ${state.data}")
                        }
                        is MapState.Failed -> {

                        }
                    }
                }
            }
        }
    }
}