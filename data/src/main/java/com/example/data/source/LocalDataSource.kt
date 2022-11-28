package com.example.data.source

import com.example.data.model.Data

interface LocalDataSource {

    suspend fun insertVaccinationCenter(data: List<Data>)

    fun getAllVaccinationCenter(): List<Data>

}