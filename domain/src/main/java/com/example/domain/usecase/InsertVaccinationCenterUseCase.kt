package com.example.domain.usecase

import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.domain.qualifiers.IoDispatcher
import com.example.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class InsertVaccinationCenterUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
)   {
    suspend fun invoke(data: List<VaccinationCenterEntityModel>) {
        return repository.insertVaccinationCenter(data)
    }
}