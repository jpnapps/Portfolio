package com.jpndev.portfolio.data.repository.dataSourceImpl

import com.jpndev.portfolio.data.db.ArticleDAO
import com.jpndev.portfolio.data.db.DAO
import com.jpndev.portfolio.data.model.Article
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.repository.dataSource.LocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LocalDataSourceImpl(
    private val articleDAO: ArticleDAO,
    private val pitemDAO: DAO
):LocalDataSource {


    override suspend fun saveArticletoDb(item: Article) {
        articleDAO.insert(item)
    }

    override  fun getArticlesFromDB(): Flow<List<Article>> {
        return articleDAO.getArticles()
    }

    override suspend fun deleteArticle(item: Article) {
        articleDAO.delete(item)
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            articleDAO.deleteAllArticle()
        }
    }


    override suspend fun savePItemtoDb(item: PItem) {
        pitemDAO.insertPItem(item)
    }

    override  fun getPItemsFromDB(): Flow<List<PItem>> {
        return pitemDAO.getPItems()
    }

    override suspend fun deletePItem(item: PItem) {
        pitemDAO.deletePItem(item)
    }

    override suspend fun clearAllPItems() {
        CoroutineScope(Dispatchers.IO).launch {
            pitemDAO.deleteAllPItem()
        }
    }

}