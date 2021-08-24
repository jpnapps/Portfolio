package com.jpndev.portfolio.ui.study.actvity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jpndev.portfolio.domain.usecase.UseCase

class LifeCycleViewModelFactory (
    private val app: Application,
    private val usecase: UseCase

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LifeCycleViewModel(
            app,
            usecase,

        ) as T
    }


}