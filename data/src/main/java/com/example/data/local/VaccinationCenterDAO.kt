package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccinationCenterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccinationCenter(data: Data)

    @Query("SELECT * FROM vaccinationCenterData")
    fun getAllVaccinationCenter(): Flow<List<Data>>

}