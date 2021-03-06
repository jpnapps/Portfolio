 package com.jpndev.portfolio.presentation.di

import android.app.Application
import androidx.room.Room
import com.jpndev.portfolio.data.db.AppDatabase
import com.jpndev.portfolio.data.db.ArticleDAO
import com.jpndev.portfolio.data.db.DAO

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {



    @Singleton
    @Provides
    fun providesDatabase(app: Application): AppDatabase {
        return  Room.databaseBuilder(app,
            AppDatabase::class.java,"jpndev_android_db").
            fallbackToDestructiveMigration().
            build()
    }

    @Singleton
    @Provides
    fun providesArticleDAO(database: AppDatabase): ArticleDAO {
        return  database.articleDao()
    }

    @Singleton
    @Provides
    fun providesDAO(database: AppDatabase): DAO {
        return  database.appDao()
    }
}














