package com.jpndev.portfolio.domain.repository

import com.jpndev.portfolio.data.model.APIResponse
import com.jpndev.portfolio.data.util.Resource

interface AppRepository {

    suspend fun getTopQA( page : Int): Resource<APIResponse>
}