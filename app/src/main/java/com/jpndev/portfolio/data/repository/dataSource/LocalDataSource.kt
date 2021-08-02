package com.jpndev.portfolio.data.repository.dataSource

import com.jpndev.portfolio.data.model.Article
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
     suspend fun saveArticletoDb(item : Article)
     fun getArticlesFromDB(): Flow<List<Article>>
     suspend fun deleteArticle(item : Article)
     suspend fun clearAll()
}