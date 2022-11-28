package com.example.vaccinationcentermapapp.ui.map

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.model.VaccinationCenterUiModel
import com.example.presentation.vm.MapState
import com.example.presentation.vm.MapViewModel
import com.example.vaccinationcentermapapp.R
import com.example.vaccinationcentermapapp.databinding.FragmentMapBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : Fragment(), PermissionListener {

    lateinit var binding: FragmentMapBinding
    private val mapViewModel: MapViewModel by viewModels()
    private var naverMap: NaverMap? = null
    private var mapView: MapView? = null
    private lateinit var markerData: List<VaccinationCenterUiModel>
    private lateinit var locationSource: FusedLocationSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllVaccinationCenter()
        observeData()
        requestPermission()

        mapView = binding.mapView
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(callback)

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(this)
            .setRationaleMessage(getString(R.string.location_msg))
            .setDeniedMessage(getString(R.string.change_permission))
            .setDeniedCloseButtonText(getString(R.string.close))
            .setGotoSettingButtonText(getString(R.string.setting))
            .setRationaleTitle(getString(R.string.location_info))
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .check()
    }

    private val callback = object : OnMapReadyCallback {
        override fun onMapReady(naverMap: NaverMap) {
            this@MapFragment.naverMap = naverMap

            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow

            crateMarker()

            binding.fbLocation.setOnClickListener {
                Toast.makeText(requireContext(), getString(R.string.move_my_location), Toast.LENGTH_SHORT).show()
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
                if (centerData.centerType == getString(R.string.center)) iconTintColor = Color.YELLOW
                if (centerData.centerType == getString(R.string.local)) iconTintColor = Color.BLUE

                naverMap?.setOnMapClickListener { _, _ ->
                    infoWindow.close()
                    if (binding.slideFrame.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        binding.slideFrame.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
                    }
                }

                setOnClickListener {
                    clickSlidingUpPanel()

                    naverMap?.moveCamera(cameraUpdate)
                    binding.vaccinationCenterModel = centerData

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

    private fun clickSlidingUpPanel() {
        if (binding.slideFrame.panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            binding.slideFrame.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED)
        }

        if (binding.slideFrame.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            binding.slideFrame.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
        }
    }

    private fun getAllVaccinationCenter() {
        mapViewModel.getAllVaccinationCenter()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mapViewModel.getVaccinationCenter.collect { state ->
                    when (state) {
                        is MapState.Loading -> {
                            binding.pb.isVisible = true
                        }

                        is MapState.Success -> {
                            binding.pb.isVisible = false
                            state.data.forEach {
                                binding.vaccinationCenterModel = it
                                markerData = state.data
                            }
                        }

                        is MapState.Failed -> {
                            Timber.e("에러 발생: ${state.message}")
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
        mapView?.onSaveInstanceState(outState)
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

    override fun onPermissionGranted() {
        Toast.makeText(requireContext(), getString(R.string.success_get_location), Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        Toast.makeText(requireContext(), getString(R.string.fail_get_location), Toast.LENGTH_SHORT).show()
    }
}