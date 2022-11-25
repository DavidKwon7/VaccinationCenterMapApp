package com.example.data.source

import com.example.data.local.VaccinationCenterDAO
import com.example.data.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val vaccinationCenterDAO: VaccinationCenterDAO
) : LocalDataSource {
    override suspend fun insertVaccinationCenter(data: List<Data>) {
        return vaccinationCenterDAO.insertVaccinationCenter(data)
    }

    override fun getAllVaccinationCenter(): List<Data> {
        return vaccinationCenterDAO.getAllVaccinationCenter()
    }
}