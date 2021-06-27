package com.gojek.casestudy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gojek.casestudy.model.Repository
import com.gojek.casestudy.model.Resource
import com.gojek.casestudy.network.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(private val repository: NetworkRepository) : ViewModel() {

    private val isViewLoading = MutableLiveData<Boolean>()
    val viewLoading: MutableLiveData<Boolean> = isViewLoading

    private val dataList = MutableLiveData<List<Repository>>().apply { value = emptyList() }
    val dataListLiveData: LiveData<List<Repository>> = dataList

    private val onMessageError = MutableLiveData<Any>()
    val onMessageErrorLiveData: LiveData<Any> = onMessageError

    private val isEmptyList = MutableLiveData<Boolean>()
    val isEmptyListLiveData: LiveData<Boolean> = this.isEmptyList

    var showError = MutableLiveData(false)
    val resource = MutableLiveData<Resource>()

    fun updateList() {
        val list = dataList.value
        val updatedList = list?.plus(list)
        dataList.value = updatedList
    }

    fun loadData(isInternetConnected: Boolean) {
        if (isInternetConnected) {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    resource.value = Resource.Loading

                    val repositories = withContext(Dispatchers.IO) {
                        repository.getRemoteRepositories()
                    }

                    if (repositories.isEmpty())
                        resource.value = Resource.Error("An alien is probably blocking your signal")
                    else
                        resource.value = Resource.Success(repositories)
                }
            }
        } else {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    val repositories = withContext(Dispatchers.IO) {
                        repository.getCachedRepositories()
                    }

                    if (repositories.isEmpty())
                        resource.value = Resource.Error("An alien is probably blocking your signal")
                    else
                        resource.value = Resource.Success(repositories)
                }
            }
        }
    }

}