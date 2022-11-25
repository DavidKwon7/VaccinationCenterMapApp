package com.example.data.remote

import com.example.common.Constants.Companion.SERVICE_KEY
import com.example.data.model.VaccinationCenterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VaccinationCenterAPI {

    @GET("15077586/v1/centers")
    suspend fun getVaccinationCenter(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = SERVICE_KEY
    ): VaccinationCenterResponse

}