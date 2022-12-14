package com.example.data.source

import com.example.common.Constants.Companion.PER_PAGE
import com.example.data.model.Data
import com.example.data.remote.VaccinationCenterAPI
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val vaccinationCenterAPI: VaccinationCenterAPI
) : RemoteDataSource {

    override suspend fun getVaccinationCenter(page: Int): List<Data>? {
        val response = vaccinationCenterAPI.getVaccinationCenter(
            page,
            PER_PAGE
        )
        return response.data
    }
}