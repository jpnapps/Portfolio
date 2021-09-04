package com.jpndev.portfolio.domain.usecase

import android.content.Context
import android.content.SharedPreferences
import com.jpndev.portfolio.data.model.APIResponse
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.model.PListResponse
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.repository.AppRepository
import com.jpndev.portfolio.utils.PrefUtils
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

/* @Inject constructor */
class UseCase (private val repository: AppRepository, private val appContext: Context,public val prefUtils:PrefUtils,public val logsource:LogSourceImpl) {

    suspend fun executeTopQA(page : Int): Resource<APIResponse> {
        return repository.getTopQA(page)
    }

    suspend fun executeDeletePItrm(item: PItem)=repository.deletePItem(item)
    suspend fun executeSavePItem(item: PItem): Long=repository.savePItem(item)
    suspend fun executeUpdatePItem(item: PItem): Int=repository.updatePItem(item)

    fun executeGetPList(): Flow<List<PItem>> {
        return repository.getPItems()
    }

    suspend fun executeDownloadRequest(url : String): Resource<ResponseBody> {
        return repository.getDownloadBody(url)
    }
    /*suspend fun execute():Flow<List<Article>>{
        return newsRepository.getSavedNews()
    }*/
}