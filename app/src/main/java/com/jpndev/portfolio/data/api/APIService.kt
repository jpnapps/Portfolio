package com.jpndev.portfolio.data.api

import com.jpndev.portfolio.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
  @GET("view")
  suspend fun getTopQA(

      @Query("id")
      page:Int
  ): Response<APIResponse>


    @GET("v2/top-headlines")
    suspend fun getTopArticles(

        @Query("page")
        page:Int
    ): Response<APIResponse>

}