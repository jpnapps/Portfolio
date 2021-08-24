package com.jpndev.portfolio.ui.study.video

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jpndev.portfolio.domain.usecase.UseCase

class VideoPlayViewModelFactory (
    private val app: Application,
    private val usecase: UseCase

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VideoPlayViewModel(
            app,
            usecase,

        ) as T
    }


}