package com.example.data.mapper

import com.example.common.Mapper
import com.example.data.model.Data
import com.example.data.model.VaccinationCenterResponse
import com.example.domain.entity.VaccinationCenterEntityModel
import javax.inject.Inject

class VaccinationCenterDataDomainMapper @Inject constructor() :
    Mapper<Data, VaccinationCenterEntityModel> {

    override fun from(i: Data?): VaccinationCenterEntityModel {
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

    override fun to(o: VaccinationCenterEntityModel?): Data {
        return Data(
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