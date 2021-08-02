package com.jpndev.portfolio.presentation.di

import com.jpndev.portfolio.domain.repository.AppRepository
import com.jpndev.portfolio.domain.usecase.UseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
   @Singleton
   @Provides
   fun provideUseCase(
      repository: AppRepository
   ): UseCase {
      return UseCase(repository)
   }



}


















