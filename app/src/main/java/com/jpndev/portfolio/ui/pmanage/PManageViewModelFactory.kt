package com.beeone.techbank.sign.kyc.sumsub

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jpndev.portfolio.domain.usecase.UseCase

class PManageViewModelFactory (
    private val app: Application,
    private val usecase: UseCase

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PManageViewModel(
            app,
            usecase,

        ) as T
    }


}