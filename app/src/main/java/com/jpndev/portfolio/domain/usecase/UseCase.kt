package com.jpndev.portfolio.domain.usecase

import com.jpndev.portfolio.data.model.APIResponse
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.repository.AppRepository

class UseCase (private val repository: AppRepository) {

    suspend fun executeTopQA(page : Int): Resource<APIResponse> {
        return repository.getTopQA(page)
    }
    /*suspend fun execute():Flow<List<Article>>{
        return newsRepository.getSavedNews()
    }*/
}