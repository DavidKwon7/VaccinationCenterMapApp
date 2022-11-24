package com.example.domain.repository

import com.example.domain.entity.VaccinationCenterEntityModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getVaccinationCenter(): Flow<List<VaccinationCenterEntityModel>>
}