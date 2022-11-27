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

// todo 1 마커 여러개 띄우기(리스트를 만들어 데이터 저장해줘야 할듯) -> 마커 관련 다양한 설정 custom
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
        mapView?.onCreate(savedInstanceState)   // why?
        mapView?.getMapAsync(callback)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)   // why?
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    // onMapReady에서 마커 실행해야 보임!!
    private val callback = object : OnMapReadyCallback {
        override fun onMapReady(naverMap: NaverMap) {
            this@MapFragment.naverMap = naverMap

            crateMarker()

            /*// 클릭 이벤트 -> 이 방법을 쓰면 마커 클릭이 아니라 맵 클릭이 되어버림.. (수정 필요)
            naverMap.setOnMapClickListener { pointF, latLng ->
                Toast.makeText(requireContext(), "${latLng.latitude}, ${latLng.longitude}", Toast.LENGTH_SHORT).show()

                // 마커 클릭 시 화면 이동
                val cameraUpdate = CameraUpdate.scrollTo(latLng)
                naverMap.moveCamera(cameraUpdate)
                naverMap.maxZoom = 18.0
                naverMap.minZoom = 5.0

            }*/
        }
    }

    private fun crateMarker() {
        val markers = mutableListOf<Marker>()
        val locationOverlay = naverMap?.locationOverlay

        markerData.forEach {
            val location = LatLng(it.lat!!.toDouble(), it.lng!!.toDouble())
            markers += Marker().apply {
                position = location

                val infoWindow = InfoWindow()
                infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(requireContext()) {
                    override fun getText(p0: InfoWindow): CharSequence {
                        return "${it.centerName}"
                        //infoWindow.marker?.tag as CharSequence? ?: ""
                    }
                }

                setOnClickListener {
                    infoWindow.open(this)
                    true
                }
                infoWindow.close()
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
}

