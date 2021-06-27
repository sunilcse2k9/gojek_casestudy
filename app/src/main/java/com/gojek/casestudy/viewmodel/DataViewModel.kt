package com.gojek.casestudy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gojek.casestudy.model.GitHubRepository
import com.gojek.casestudy.model.Resource
import com.gojek.casestudy.network.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(private val repository: DataRepository) : ViewModel() {

    val resource = MutableLiveData<Resource>()

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

    fun sortByNames(gitHubRepositories: List<GitHubRepository>) {
        viewModelScope.launch {
            resource.value = Resource.Success(repository.sortByNames(gitHubRepositories))
        }
    }

    fun sortByStars(gitHubRepositories: List<GitHubRepository>) {
        viewModelScope.launch {
            resource.value = Resource.Success(repository.sortByStars(gitHubRepositories))
        }
    }
}