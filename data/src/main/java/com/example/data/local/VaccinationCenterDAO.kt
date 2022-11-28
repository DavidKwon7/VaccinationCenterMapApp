package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.Data

@Dao
interface VaccinationCenterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccinationCenter(data: List<Data>)

    @Query("SELECT * FROM vaccinationCenterData")
    fun getAllVaccinationCenter(): List<Data>

}