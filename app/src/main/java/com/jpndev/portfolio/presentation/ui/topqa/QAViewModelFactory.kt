package com.jpndev.portfolio.presentation.ui.topqa

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jpndev.portfolio.domain.usecase.UseCase

class QAViewModelFactory (
    private val app: Application,
    private val usecase:UseCase

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopQAViewModel(
            app,
            usecase,

        ) as T
    }


}