package com.jpndev.portfolio.presentation.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jpndev.portfolio.domain.usecase.UseCase

class MainViewModelFactory (
    private val app: Application,
    public val usecase: UseCase

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            app,
            usecase,

        ) as T
    }


}