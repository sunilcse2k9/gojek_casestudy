package com.gojek.casestudy.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gojek.casestudy.network.repository.NetworkRepository


class ViewModelFactory(private val repository: NetworkRepository) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataViewModel(repository) as T
    }
}