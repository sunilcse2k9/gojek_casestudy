package com.gojek.casestudy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gojek.casestudy.model.GitHubRepository
import com.gojek.casestudy.model.Resource
import com.gojek.casestudy.repository.Repository
import com.gojek.casestudy.viewmodel.MainViewModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val resourceObserver: Observer<Resource> = mock()
    private val repository: Repository = TestRepository()
    private val viewModel = MainViewModel(repository)
    private val repositories = mutableListOf<GitHubRepository>()

    @Before
    fun setupViewModel() {
        viewModel.resource.observeForever(resourceObserver)
        repositories.add(
            GitHubRepository(
                "xyz",
                "Test repo",
                GitHubRepository.Owner("", ""),
                "C++",
                200,
                200)
        )

        repositories.add(
            GitHubRepository(
                "abc",
                "Test repo",
                GitHubRepository.Owner("", ""),
                "C++",
                100,
                200)
        )
    }

    @Test
    fun loadDataCallsGetRepositories_WithInternet() {
        val isInternetConnected = true
        viewModel.loadData(isInternetConnected)
        CoroutineScope(Dispatchers.IO).launch {
            verify(repository).getRepositories(isInternetConnected)
        }
    }

    @Test
    fun loadDataCallsGetRepositories_Offline() {
        val isInternetConnected = false
        viewModel.loadData(isInternetConnected)
        CoroutineScope(Dispatchers.IO).launch {
            verify(repository).getRepositories(isInternetConnected)
        }
    }

    @Test
    fun sortByNamesCallsSortByNames() {
        viewModel.sortByNames(listOf())
        CoroutineScope(Dispatchers.IO).launch {
            verify(repository).sortByNames(listOf())
        }
    }

    @Test
    fun sortByStarsCallsSortByStars() {
        viewModel.sortByStars(listOf())
        CoroutineScope(Dispatchers.IO).launch {
            verify(repository).sortByStars(listOf())
        }
    }
}