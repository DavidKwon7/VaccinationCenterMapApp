package com.example.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Mapper
import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.domain.usecase.GetVaccinationCenterUseCase
import com.example.presentation.model.VaccinationCenterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getVaccinationCenterUseCase: GetVaccinationCenterUseCase,
    private val vaccinationCenterMapper: Mapper<VaccinationCenterUiModel, VaccinationCenterEntityModel>
) : ViewModel() {

    private var _getVaccinationCenter = MutableStateFlow<SplashState>(SplashState.Loading)
    val getVaccinationCenter: StateFlow<SplashState> get() = _getVaccinationCenter

    fun getVaccinationCenter() =
        viewModelScope.launch {
            _getVaccinationCenter.value = SplashState.Loading
            getVaccinationCenterUseCase.invoke()
                .catch { e ->
                    _getVaccinationCenter.value = SplashState.Failed(e)
                }.collect{ vaccinationCenter ->
                    _getVaccinationCenter.value = SplashState.Success(vaccinationCenterMapper.toList(vaccinationCenter))
                }
        }


}

sealed class SplashState() {
    object Loading : SplashState()
    class Success(var data: List<VaccinationCenterUiModel>) : SplashState()
    class Failed(var message: Throwable) : SplashState()
}