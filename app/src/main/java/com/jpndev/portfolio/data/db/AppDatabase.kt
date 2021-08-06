package com.jpndev.portfolio.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jpndev.portfolio.data.model.Article
import com.jpndev.portfolio.data.model.PItem

import com.jpndev.portfolio.data.model.QA


@Database(entities = [QA::class,Article::class,PItem::class],
version = 2,
exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

abstract fun articleDao(): ArticleDAO
    abstract fun appDao(): DAO
}