package com.example.vaccinationcentermapapp.ui.map

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.presentation.model.VaccinationCenterUiModel
import com.example.presentation.vm.MapState
import com.example.presentation.vm.MapViewModel
import com.example.vaccinationcentermapapp.R
import com.example.vaccinationcentermapapp.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// todo 2  lifeCycle - mapView 수정


@AndroidEntryPoint
class MapFragment : Fragment() {

    lateinit var binding: FragmentMapBinding
    private val mapViewModel: MapViewModel by viewModels()

    private var naverMap: NaverMap? = null
    private var mapView: MapView? = null

    private lateinit var markerData: List<VaccinationCenterUiModel>

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
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(callback)

        binding.fbLocation.setOnClickListener {
            Toast.makeText(requireContext(), "현재 위치", Toast.LENGTH_SHORT).show()
        }
    }

    // onMapReady에서 마커 실행해야 보임!!
    private val callback = object : OnMapReadyCallback {
        override fun onMapReady(naverMap: NaverMap) {
            this@MapFragment.naverMap = naverMap

            crateMarker()
        }
    }

    private fun crateMarker() {
        val markers = mutableListOf<Marker>()

        val infoWindow = InfoWindow()

        markerData.forEach { centerData ->
            val location = LatLng(centerData.lat!!.toDouble(), centerData.lng!!.toDouble())
            val cameraUpdate = CameraUpdate.scrollTo(location)

            infoWindow.map = null

            markers += Marker().apply {
                position = location
                if (centerData.centerType == "중앙/권역") iconTintColor = Color.YELLOW
                if (centerData.centerType == "지역") iconTintColor = Color.BLUE

                naverMap?.setOnMapClickListener { pointF, latLng ->
                    infoWindow.close()
                }

                setOnClickListener {
                    naverMap?.moveCamera(cameraUpdate)

                    infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(requireContext()) {
                        override fun getText(p0: InfoWindow): CharSequence {
                            return " 센터명: ${centerData.centerName} " +
                                    "\n 장소: ${centerData.facilityName} " +
                                    "\n 주소지: ${centerData.address}, " +
                                    "\n 전화번호: ${centerData.phoneNumber}, " +
                                    "\n 생성일: ${centerData.createdAt}"
                        }
                    }
                    val selectedMarker = it as Marker
                    if (selectedMarker.infoWindow == null) {
                        infoWindow.open(this)
                    } else {
                        infoWindow.close()
                    }
                    true
                }
            }

            markers.forEach { marker ->
                marker.map = naverMap
            }
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

                            }
                            Log.d("Map Test", "observeData: ${state.data}")

                            state.data.forEach {
                                Log.d("TAG1", "observeData: ${it.lng}")

                                markerData = state.data
                            }
                        }
                        is MapState.Failed -> {

                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)   // why?
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}

