package com.example.vaccinationcentermapapp.di

import android.content.Context
import androidx.room.Room
import com.example.common.Constants
import com.example.data.local.VaccinationCenterDAO
import com.example.data.local.VaccinationCenterDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): VaccinationCenterDataBase {
        return Room.databaseBuilder(context, VaccinationCenterDataBase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(vaccinationCenterDataBase: VaccinationCenterDataBase): VaccinationCenterDAO {
        return vaccinationCenterDataBase.getVaccinationCenter()
    }
}