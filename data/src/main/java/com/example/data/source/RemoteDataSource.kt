package com.example.data.source

import com.example.data.model.Data

interface RemoteDataSource {
    suspend fun getVaccinationCenter(page: Int): List<Data>?
}