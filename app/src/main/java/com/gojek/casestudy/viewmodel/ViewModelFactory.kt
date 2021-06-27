package com.gojek.casestudy.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gojek.casestudy.repository.DataRepository


class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}