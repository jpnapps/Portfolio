package com.jpndev.portfolio.presentation.di

import android.app.Application
import com.jpndev.portfolio.domain.usecase.UseCase
import com.jpndev.portfolio.presentation.ui.topqa.QAViewModelFactory

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
  fun provideQAViewModelFactory(
        application: Application,
        useCase: UseCase
  ): QAViewModelFactory {
      return QAViewModelFactory(
          application,
          useCase

      )
  }
}








