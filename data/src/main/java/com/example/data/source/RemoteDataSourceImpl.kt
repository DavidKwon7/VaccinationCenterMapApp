package com.example.data.source

import android.util.Log
import com.example.common.Constants.Companion.PER_PAGE
import com.example.common.Constants.Companion.START_PAGE
import com.example.data.model.Data
import com.example.data.model.VaccinationCenterResponse
import com.example.data.remote.VaccinationCenterAPI
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val vaccinationCenterAPI: VaccinationCenterAPI
) : RemoteDataSource {

    override suspend fun getVaccinationCenter(): List<Data>? {
        val response =  vaccinationCenterAPI.getVaccinationCenter(
            START_PAGE,
            PER_PAGE
        )
        return response.data
    }
}