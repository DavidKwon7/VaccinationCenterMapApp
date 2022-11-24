package com.example.domain.usecase

import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.domain.qualifiers.IoDispatcher
import com.example.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetVaccinationCenterUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun invoke(): Flow<List<VaccinationCenterEntityModel>> {
        return repository.getVaccinationCenter()
            .flowOn(dispatcher)
    }
}