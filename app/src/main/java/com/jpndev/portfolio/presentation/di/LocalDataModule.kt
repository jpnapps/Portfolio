package com.jpndev.newsapiclient.presentation.di

import com.jpndev.portfolio.data.db.ArticleDAO
import com.jpndev.portfolio.data.repository.dataSource.LocalDataSource
import com.jpndev.portfolio.data.repository.dataSourceImpl.LocalDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(
        articleDAO: ArticleDAO
    ): LocalDataSource {
       return LocalDataSourceImpl(articleDAO)
    }

}












