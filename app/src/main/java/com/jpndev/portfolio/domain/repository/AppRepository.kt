package com.jpndev.portfolio.domain.repository

import com.jpndev.portfolio.data.model.APIResponse
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.model.PListResponse
import com.jpndev.portfolio.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getTopQA( page : Int): Resource<APIResponse>
    //suspend fun getPList( page : Int): PListResponse

    suspend fun savePItem(article: PItem): Long
    suspend fun updatePItem(article: PItem): Int
    suspend fun deletePItem(article: PItem)
    fun getPItems(): Flow<List<PItem>>

}