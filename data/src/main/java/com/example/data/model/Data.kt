package com.example.data.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "vaccinationCenterData"
)
data class Data(
    @SerializedName("address")
    val address: String?,
    @SerializedName("centerName")
    val centerName: String?,
    @SerializedName("centerType")
    val centerType: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("facilityName")
    val facilityName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lat")
    val lat: String?,
    @SerializedName("lng")
    val lng: String?,
    @SerializedName("org")
    val org: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("sido")
    val sido: String?,
    @SerializedName("sigungu")
    val sigungu: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("zipCode")
    val zipCode: String?
)