package com.example.data.model


import com.google.gson.annotations.SerializedName

data class VaccinationCenterResponse(

    @SerializedName("currentCount")
    val currentCount: Int?,
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("matchCount")
    val matchCount: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("perPage")
    val perPage: Int?,
    @SerializedName("totalCount")
    val totalCount: Int?

)