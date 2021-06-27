package com.gojek.casestudy.network.repository

import com.gojek.casestudy.model.GitHubRepository

interface Repository {
    suspend fun getCachedRepositories(): List<GitHubRepository>
    suspend fun getRemoteRepositories(): List<GitHubRepository>
    suspend fun sortByNames(gitHubRepositories: List<GitHubRepository>): List<GitHubRepository>
    suspend fun sortByStars(gitHubRepositories: List<GitHubRepository>): List<GitHubRepository>
}