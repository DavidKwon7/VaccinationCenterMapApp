package com.example.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Mapper
import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.domain.usecase.GetAllVaccinationCenterUseCase
import com.example.presentation.model.VaccinationCenterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAllVaccinationCenterUseCase: GetAllVaccinationCenterUseCase,
    private val vaccinationCenterMapper: Mapper<VaccinationCenterUiModel, VaccinationCenterEntityModel>
) : ViewModel() {

    private var _getVaccinationCenter = MutableStateFlow<MapState>(MapState.Loading)
    val getVaccinationCenter: StateFlow<MapState> get() = _getVaccinationCenter

    fun getAllVaccinationCenter() =
        viewModelScope.launch {
            _getVaccinationCenter.value = MapState.Loading
            getAllVaccinationCenterUseCase.invoke()
                .catch { e ->
                    _getVaccinationCenter.value = MapState.Failed(e)
                }.collect { vaccinationCenter ->
                    _getVaccinationCenter.value =
                        MapState.Success(vaccinationCenterMapper.toList(vaccinationCenter))
                }
        }
}

sealed class MapState() {
    object Loading : MapState()
    class Success(var data: List<VaccinationCenterUiModel>) : MapState()
    class Failed(var message: Throwable) : MapState()
}