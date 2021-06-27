package com.gojek.casestudy.network.repository

import com.gojek.casestudy.model.Repository

interface Repository {
    suspend fun getCachedRepositories(): List<Repository>
    suspend fun getRemoteRepositories(): List<Repository>
    suspend fun sortByNames(repositories: List<Repository>): List<Repository>
    suspend fun sortByStars(repositories: List<Repository>): List<Repository>
}