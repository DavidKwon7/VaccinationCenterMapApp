package com.example.presentation.mapper

import com.example.common.Mapper
import com.example.domain.entity.VaccinationCenterEntityModel
import com.example.presentation.model.VaccinationCenterUiModel
import javax.inject.Inject


class VaccinationCenterUiDomainMapper @Inject constructor() : Mapper<VaccinationCenterUiModel, VaccinationCenterEntityModel>{
    override fun from(i: VaccinationCenterUiModel?): VaccinationCenterEntityModel {
        return VaccinationCenterEntityModel(
            address = i?.address,
            centerName = i?.centerName,
            centerType = i?.centerType,
            createdAt = i?.createdAt,
            facilityName = i?.facilityName,
            id = i?.id,
            lat = i?.lat,
            lng = i?.lng,
            org = i?.org,
            phoneNumber = i?.phoneNumber,
            sido = i?.sido,
            sigungu = i?.sigungu,
            updatedAt = i?.updatedAt,
            zipCode = i?.zipCode
        )
    }

    override fun to(o: VaccinationCenterEntityModel?): VaccinationCenterUiModel {
        return VaccinationCenterUiModel(
            address = o?.address,
            centerName = o?.centerName,
            centerType = o?.centerType,
            createdAt = o?.createdAt,
            facilityName = o?.facilityName,
            id = o?.id,
            lat = o?.lat,
            lng = o?.lng,
            org = o?.org,
            phoneNumber = o?.phoneNumber,
            sido = o?.sido,
            sigungu = o?.sigungu,
            updatedAt = o?.updatedAt,
            zipCode = o?.zipCode
        )
    }
}