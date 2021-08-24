package com.jpndev.portfolio.ui.manage_log

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jpndev.portfolio.domain.usecase.UseCase

class ViewLogosViewModelFactory (
    private val app: Application,
    private val usecase: UseCase

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewLogosViewModel(
            app,
            usecase,

        ) as T
    }


}