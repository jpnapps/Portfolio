package com.jpndev.portfolio.domain.usecase

import android.content.Context
import com.jpndev.portfolio.data.model.APIResponse
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.model.PListResponse
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor (private val repository: AppRepository, private val appContext: Context) {

    suspend fun executeTopQA(page : Int): Resource<APIResponse> {
        return repository.getTopQA(page)
    }

    suspend fun executeDeletePItrm(item: PItem)=repository.deletePItem(item)
    suspend fun executeSavePItem(item: PItem)=repository.savePItem(item)

    fun executeGetPList(): Flow<List<PItem>> {
        return repository.getPItems()
    }


    /*suspend fun execute():Flow<List<Article>>{
        return newsRepository.getSavedNews()
    }*/
}