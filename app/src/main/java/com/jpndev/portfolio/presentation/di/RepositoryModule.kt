package com.jpndev.newsapiclient.presentation.di


import com.jpndev.portfolio.data.repository.AppRepositoryImpl
import com.jpndev.portfolio.data.repository.dataSource.LocalDataSource
import com.jpndev.portfolio.data.repository.dataSource.RemoteDataSource
import com.jpndev.portfolio.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAppRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): AppRepository {
        return AppRepositoryImpl(remoteDataSource,localDataSource)
    }

}














