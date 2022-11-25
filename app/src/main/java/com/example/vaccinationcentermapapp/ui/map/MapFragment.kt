package com.example.vaccinationcentermapapp.ui.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
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
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment() {

    lateinit var binding: FragmentMapBinding
    private val mapViewModel: MapViewModel by viewModels()

    private var naverMap: NaverMap? = null
    private var mapView: MapView? = null

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

        mapView = binding.mapView
        mapView?.onCreate(savedInstanceState)   // why?
        mapView?.getMapAsync(callback)

        makeMarker()
    }


    private fun makeMarker() {
        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.icon = OverlayImage.fromResource(R.drawable.ic_launcher_foreground)
        marker.map = naverMap
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)   // why?
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    // onMapReady에서 마커 실행해야 보임
    private val callback = object : OnMapReadyCallback {
        override fun onMapReady(naverMap: NaverMap) {
            this@MapFragment.naverMap = naverMap

            makeMarker()
        }
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

                                //createMarker(it.lat, it.lng)
                            }
                            Log.d("Map Test", "observeData: ${state.data}")

                            state.data.forEach {
                                Log.d("TAG1", "observeData: ${it.lng}")
                                createMarker(it.lat, it.lng)
                            }
                        }
                        is MapState.Failed -> {

                        }
                    }
                }
            }
        }
    }

    private fun createMarker(lat: String?, lng: String?) {
        /*val marker = Marker()
        if (lat != null && lng != null) {
            marker.position = LatLng(lat.toDouble(), lng.toDouble())
        }
        marker.map = naverMap*/
    }
}