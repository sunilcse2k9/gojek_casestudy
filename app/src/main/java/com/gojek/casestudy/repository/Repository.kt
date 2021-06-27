package com.gojek.casestudy.repository

import com.gojek.casestudy.model.GitHubRepository

interface Repository {
    suspend fun getRepositories(isInternetConnected: Boolean): List<GitHubRepository>
    suspend fun sortByNames(gitHubRepositories: List<GitHubRepository>): List<GitHubRepository>
    suspend fun sortByStars(gitHubRepositories: List<GitHubRepository>): List<GitHubRepository>
}