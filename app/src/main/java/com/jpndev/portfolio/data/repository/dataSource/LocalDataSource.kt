package com.jpndev.portfolio.data.repository.dataSource

import com.jpndev.portfolio.data.model.Article
import com.jpndev.portfolio.data.model.PItem
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
     suspend fun saveArticletoDb(item : Article)
     fun getArticlesFromDB(): Flow<List<Article>>
     suspend fun deleteArticle(item : Article)
     suspend fun clearAll()


     suspend fun savePItemtoDb(item : PItem): Long
     fun getPItemsFromDB(): Flow<List<PItem>>
     suspend fun updatePItem(pitem: PItem) : Int
     suspend fun deletePItem(item : PItem)
     suspend fun clearAllPItems()
}