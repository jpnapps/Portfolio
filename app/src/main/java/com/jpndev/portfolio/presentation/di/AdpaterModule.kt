package com.jpndev.portfolio.presentation.di

import com.jpndev.portfolio.presentation.ui.topqa.QAAdapter

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdpaterModule {

   @Singleton
   @Provides
   fun provideGetNewsAdapter(

   ): QAAdapter {
      return QAAdapter()
   }
}


















