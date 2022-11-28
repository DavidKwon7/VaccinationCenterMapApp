package com.example.domain.usecase

import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.domain.repository.Repository
import javax.inject.Inject

class InsertVaccinationCenterUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend fun invoke(data: List<VaccinationCenterEntityModel>) {
        return repository.insertVaccinationCenter(data)
    }
}