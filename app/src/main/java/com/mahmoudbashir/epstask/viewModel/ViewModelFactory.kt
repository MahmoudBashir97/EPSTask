package com.mahmoudbashir.epstask.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudbashir.epstask.repository.StRepository

class ViewModelFactory(val app: Application,
private val stRepo:StRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StViewModel(app,stRepo) as T
    }
}