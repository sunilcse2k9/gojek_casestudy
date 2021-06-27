package com.gojek.casestudy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gojek.casestudy.model.GitHubRepository
import com.gojek.casestudy.model.Resource
import com.gojek.casestudy.repository.DataRepository
import com.gojek.casestudy.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: Repository) : ViewModel() {

    val resource = MutableLiveData<Resource>()

    fun loadData(isInternetConnected: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                resource.value = Resource.Loading

                val repositories = withContext(Dispatchers.IO) {
                    repository.getRepositories(isInternetConnected)
                }

                if (repositories.isEmpty())
                    resource.value = Resource.Error("An alien is probably blocking your signal")
                else
                    resource.value = Resource.Success(repositories)
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