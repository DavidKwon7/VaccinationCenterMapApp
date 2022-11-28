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
import androidx.core.view.isVisible
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
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.sothree.slidinguppanel.SlidingUpPanelLayout
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
    private lateinit var locationSource: FusedLocationSource


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

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap?.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



    // onMapReady에서 마커 실행해야 보임!!
    private val callback = object : OnMapReadyCallback {
        override fun onMapReady(naverMap: NaverMap) {
            this@MapFragment.naverMap = naverMap

            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow

            crateMarker()

            binding.fbLocation.setOnClickListener {
                Toast.makeText(requireContext(), "현재 위치로 이동", Toast.LENGTH_SHORT).show()
                //naverMap.locationSource = locationSource
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
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
                    if (binding.slideFrame.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        binding.slideFrame.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
                    }
                }

                setOnClickListener {
                    naverMap?.moveCamera(cameraUpdate)
                    binding.vaccinationCenterModel = centerData

                    if (binding.slideFrame.panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                        binding.slideFrame.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED)
                    }


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
                            binding.pb.isVisible = true
                        }
                        is MapState.Success -> {
                            binding.pb.isVisible = false
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
                            state.message.printStackTrace()
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}

