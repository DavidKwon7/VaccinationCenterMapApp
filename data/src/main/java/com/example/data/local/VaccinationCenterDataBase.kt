package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.Data

@Database(
    entities = [Data::class],
    version = 1
)
abstract class VaccinationCenterDataBase : RoomDatabase() {
    abstract fun getVaccinationCenter(): VaccinationCenterDAO
}