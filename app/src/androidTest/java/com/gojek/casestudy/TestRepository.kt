package com.gojek.casestudy

import com.gojek.casestudy.model.GitHubRepository
import com.gojek.casestudy.repository.Repository

class TestRepository: Repository {
    override suspend fun getRepositories(isInternetConnected: Boolean): List<GitHubRepository> {
        return mutableListOf()
    }

    override suspend fun sortByNames(gitHubRepositories: List<GitHubRepository>): List<GitHubRepository> {
        return mutableListOf()
    }

    override suspend fun sortByStars(gitHubRepositories: List<GitHubRepository>): List<GitHubRepository> {
        return mutableListOf()
    }
}