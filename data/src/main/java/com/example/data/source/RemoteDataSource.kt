package com.example.data.source

import com.example.data.model.VaccinationCenterResponse

interface RemoteDataSource {

    suspend fun getVaccinationCenter(): List<VaccinationCenterResponse>
}