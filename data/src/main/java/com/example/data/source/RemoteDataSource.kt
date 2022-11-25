package com.example.data.source

import com.example.data.model.Data
import com.example.data.model.VaccinationCenterResponse

interface RemoteDataSource {

    suspend fun getVaccinationCenter(): List<Data>?
}