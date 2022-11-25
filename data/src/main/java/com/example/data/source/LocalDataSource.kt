package com.example.data.source

import com.example.data.model.Data
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertVaccinationCenter(data: List<Data>)

    fun getAllVaccinationCenter(): List<Data>
}