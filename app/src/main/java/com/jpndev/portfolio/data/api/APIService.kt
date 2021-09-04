package com.jpndev.portfolio.data.api

import com.jpndev.portfolio.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Streaming


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


    @Streaming
    @GET
    fun downloadFileWithDynamicUrlSync(@Url fileUrl: String): Call<ResponseBody>

    @Streaming
    @GET
    suspend fun downloadFileWithDynamicUrlSyncResponse(@Url fileUrl: String): Response<ResponseBody>
}