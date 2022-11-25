package com.example.vaccinationcentermapapp.di

import com.example.common.Mapper
import com.example.data.mapper.VaccinationCenterDataDomainMapper
import com.example.data.model.Data
import com.example.data.model.VaccinationCenterResponse
import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.presentation.mapper.VaccinationCenterUiDomainMapper
import com.example.presentation.model.VaccinationCenterUiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindsVaccinationCenterDataDomainMapper(mapper: VaccinationCenterDataDomainMapper): Mapper<Data, VaccinationCenterEntityModel>

    @Binds
    abstract fun bindsVaccinationCenterUiDomainMapper(mapper: VaccinationCenterUiDomainMapper): Mapper<VaccinationCenterUiModel, VaccinationCenterEntityModel>
}