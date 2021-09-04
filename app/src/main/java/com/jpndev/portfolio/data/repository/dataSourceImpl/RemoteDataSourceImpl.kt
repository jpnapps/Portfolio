package com.jpndev.portfolio.data.repository.dataSourceImpl

import com.jpndev.portfolio.data.api.APIService
import com.jpndev.portfolio.data.model.APIResponse
import com.jpndev.portfolio.data.repository.dataSource.RemoteDataSource
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class RemoteDataSourceImpl(
        private val apiService: APIService
):RemoteDataSource {
    override suspend fun getTopQA(page: Int): Response<APIResponse> {

        return  apiService.getTopQA(page)
    }

    override suspend fun getDownloadBody(url: String): Response<ResponseBody> {
        return  apiService.downloadFileWithDynamicUrlSyncResponse(url)
    }

}