package com.example.vaccinationcentermapapp.di

import com.example.data.repository.RepositoryImpl
import com.example.data.source.LocalDataSource
import com.example.data.source.LocalDataSourceImpl
import com.example.data.source.RemoteDataSource
import com.example.data.source.RemoteDataSourceImpl
import com.example.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindsLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    abstract fun bindsRepository(
        repositoryImpl: RepositoryImpl
    ): Repository
}