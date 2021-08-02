package com.jpndev.portfolio.data.repository.dataSource

import com.jpndev.portfolio.data.model.APIResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getTopQA(page : Int):Response<APIResponse>
    //suspend fun getSearchedNews(country : String,search_query : String, page : Int):Response<APIResponse>
}