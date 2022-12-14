package com.example.data.repository

import com.example.common.Mapper
import com.example.data.model.Data
import com.example.data.source.LocalDataSource
import com.example.data.source.RemoteDataSource
import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val vaccinationCenterMapper: Mapper<Data, VaccinationCenterEntityModel>
) : Repository {

    override suspend fun getVaccinationCenter(page: Int): Flow<List<VaccinationCenterEntityModel>> {
        return flow {
            val data = remoteDataSource.getVaccinationCenter(page)
            emit(vaccinationCenterMapper.fromList(data))
        }
    }

    override suspend fun insertVaccinationCenter(data: List<VaccinationCenterEntityModel>) {
        return localDataSource.insertVaccinationCenter(
            vaccinationCenterMapper.toList(data)
        )
    }

    override fun getAllVaccinationCenter(): Flow<List<VaccinationCenterEntityModel>> {
            return flow {
                val data = localDataSource.getAllVaccinationCenter()
                emit(vaccinationCenterMapper.fromList(data))
            }
    }
}