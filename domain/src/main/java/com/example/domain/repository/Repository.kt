package com.example.domain.repository

import com.example.domain.entity.VaccinationCenterEntityModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getVaccinationCenter(page: Int): Flow<List<VaccinationCenterEntityModel>>

    suspend fun insertVaccinationCenter(data: List<VaccinationCenterEntityModel>)

    fun getAllVaccinationCenter(): Flow<List<VaccinationCenterEntityModel>>

}