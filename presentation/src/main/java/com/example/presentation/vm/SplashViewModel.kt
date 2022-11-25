package com.example.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Mapper
import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.domain.usecase.GetAllVaccinationCenterUseCase
import com.example.domain.usecase.GetVaccinationCenterUseCase
import com.example.domain.usecase.InsertVaccinationCenterUseCase
import com.example.presentation.model.VaccinationCenterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getVaccinationCenterUseCase: GetVaccinationCenterUseCase,
    private val insetVaccinationCenterUseCase: InsertVaccinationCenterUseCase,
    private val vaccinationCenterMapper: Mapper<VaccinationCenterUiModel, VaccinationCenterEntityModel>
) : ViewModel() {

    private var _getVaccinationCenter = MutableStateFlow<SplashState>(SplashState.Loading)
    val getVaccinationCenter: StateFlow<SplashState> get() = _getVaccinationCenter

    private var _insertFlow = MutableStateFlow<List<VaccinationCenterUiModel>>(listOf())
    val insertFlow: StateFlow<List<VaccinationCenterUiModel>> get() = _insertFlow

    fun getVaccinationCenter(page: Int) =
        viewModelScope.launch {
            _getVaccinationCenter.value = SplashState.Loading
            getVaccinationCenterUseCase.invoke(page)
                .catch { e ->
                    _getVaccinationCenter.value = SplashState.Failed(e)
                }.collect{ vaccinationCenter ->
                    _getVaccinationCenter.value = SplashState.Success(vaccinationCenterMapper.toList(vaccinationCenter))
                }
        }

    // annotation 붙이기
    fun insertVaccinationCenter(data: List<VaccinationCenterUiModel>) =
        viewModelScope.launch(Dispatchers.IO) {
            //_insertFlow.value = SplashState.Loading
            insetVaccinationCenterUseCase.invoke(vaccinationCenterMapper.fromList(data))
        }


}

sealed class SplashState() {
    object Loading : SplashState()
    class Success(var data: List<VaccinationCenterUiModel>) : SplashState()
    class Failed(var message: Throwable) : SplashState()
}