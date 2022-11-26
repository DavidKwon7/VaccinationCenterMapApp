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
import com.example.presentation.model.VaccinationCenterUiModel
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

// todo 1 마커 여러개 띄우기(리스트를 만들어 데이터 저장해줘야 할듯) -> 마커 관련 다양한 설정 custom
// todo 2  lifeCycle - mapView 수정


@AndroidEntryPoint
class MapFragment : Fragment() {

    lateinit var binding: FragmentMapBinding
    private val mapViewModel: MapViewModel by viewModels()

    private var naverMap: NaverMap? = null
    private var mapView: MapView? = null

    // 이렇게 전역 변수를 선언하는 것 말고는 방법이 없을까?
    private lateinit var location: Location

    private lateinit var markerData: List<VaccinationCenterUiModel> // 추후에 삭제 !

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

    // marker 를 mutableList에 넣어줘서 마커 여러개를 생성..?
    // https://keoroo.tistory.com/42 참조하자
    private fun makeMarker() {
        val marker = Marker()
        //marker.position = LatLng(37.5670135, 126.9783740)
        Log.d("Location Log", "makeMarker: ${location.lat}, ${location.lng}")
        //marker.position = LatLng(location.lat, location.lng)
        markerData.forEach {
            marker.position = LatLng(it.lat!!.toDouble(), it.lng!!.toDouble())
            Log.d("TAG", "makeMarker: ${it.lat}")   // 로그 lat 여러개 찍히는 것 확인
        }
        //marker.icon = OverlayImage.fromResource(R.drawable.ic_launcher_foreground)
        marker.map = naverMap
    }

    private fun addMarker() {
        val markers = mutableListOf<Marker>()
        markerData.forEach {
            markers += Marker().apply {
                position = LatLng(it.lat!!.toDouble(), it.lng!!.toDouble())
            }
            markers.forEach { marker ->
                marker.map = naverMap
            }
        }
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

            //makeMarker()
            addMarker()
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
                                //createMarker(it.lat, it.lng)
                                location = Location(it.lat!!.toDouble(), it.lng!!.toDouble())   // !! 안 쓰려고 했지만 일단 어쩔 수 없네..
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

data class Location(
    val lat: Double,
    val lng: Double
)