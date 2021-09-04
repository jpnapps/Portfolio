package com.jpndev.portfolio.data.repository

import com.jpndev.portfolio.data.model.APIResponse
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.model.PListResponse
import com.jpndev.portfolio.data.repository.dataSource.LocalDataSource
import com.jpndev.portfolio.data.repository.dataSource.RemoteDataSource
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.Response

class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
):AppRepository {


    override suspend fun getTopQA(page : Int): Resource<APIResponse> {
        return responseToResource(remoteDataSource.getTopQA(page))
    }

 /*   override suspend fun getPList(page: Int): PListResponse {
        return PListResponse()
    }*/

    override suspend fun savePItem(item: PItem) : Long{
      return  localDataSource.savePItemtoDb(item)
    }

    override suspend fun updatePItem(item: PItem): Int {
        return  localDataSource.updatePItem(item)
    }

    override suspend fun deletePItem(item: PItem) {
        localDataSource.deletePItem(item)
    }

    override fun getPItems(): Flow<List<PItem>> {
        return localDataSource.getPItemsFromDB()
    }

    override suspend fun getDownloadBody(url: String): Resource<ResponseBody> {
        return responseToResourceBody(remoteDataSource.getDownloadBody(url))
    }


    private fun responseToResource(response:Response<APIResponse>):Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResourceBody(response: Response<ResponseBody>):Resource<ResponseBody>{
        response.body()?.let {
            result->
            return Resource.Success(result)
        }

        return Resource.Error(response.message())
    }




   /* override suspend fun saveNews(QA: QA) {
        localDataSource.saveArticletoDb(QA)
    }

    override suspend fun deleteNews(QA: QA) {
        localDataSource.deleteArticle(QA)
    }

    override  fun getSavedNews(): Flow<List<QA>> {
      return  localDataSource.getArticlesFromDB()
    }*/
}