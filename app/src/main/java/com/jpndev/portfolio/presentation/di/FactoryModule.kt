package com.jpndev.portfolio.presentation.di

import android.app.Application
import com.jpndev.portfolio.domain.usecase.UseCase
import com.jpndev.portfolio.presentation.ui.topqa.QAViewModelFactory
import com.jpndev.portfolio.ui.manage_log.ViewLogosViewModelFactory
import com.jpndev.portfolio.ui.pmanage.AddPItemViewModelFactory
import com.jpndev.portfolio.ui.pmanage.PManageViewModelFactory
import com.jpndev.portfolio.ui.study.actvity.LifeCycleViewModelFactory
import com.jpndev.portfolio.ui.study.download.DownloadViewModelFactory
import com.jpndev.portfolio.ui.study.video.VideoPlayViewModelFactory

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

    @Singleton
    @Provides
    fun providePManageViewModelFactory(
        application: Application,
        useCase: UseCase
    ): PManageViewModelFactory {
        return PManageViewModelFactory(
                application,
                useCase

        )
    }

    @Singleton
    @Provides
    fun provideAddPItemViewModelFactory(
        application: Application,
        useCase: UseCase
    ): AddPItemViewModelFactory {
        return AddPItemViewModelFactory(
                application,
                useCase

        )
    }

    @Singleton
    @Provides
    fun provideLifeCycleViewModelFactory(
        application: Application,
        useCase: UseCase
    ): LifeCycleViewModelFactory {
        return LifeCycleViewModelFactory(
                application,
                useCase

        )
    }



    @Singleton
    @Provides
    fun provideViewLogosViewModelFactory(
        application: Application,
        useCase: UseCase
    ): ViewLogosViewModelFactory {
        return ViewLogosViewModelFactory(
                application,
                useCase

        )
    }

    @Singleton
    @Provides
    fun provideVVideoPlayViewModelFactory(
        application: Application,
        useCase: UseCase
    ): VideoPlayViewModelFactory {
        return VideoPlayViewModelFactory(
                application,
                useCase

        )
    }
    @Singleton
    @Provides
    fun provideDownloadViewModelFactory(
        application: Application,
        useCase: UseCase
    ): DownloadViewModelFactory {
        return DownloadViewModelFactory(
                application,
                useCase

        )
    }
}








