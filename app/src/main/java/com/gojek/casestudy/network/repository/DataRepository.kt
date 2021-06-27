package com.gojek.casestudy.network.repository

import com.gojek.casestudy.model.Repository

interface DataRepository {
    suspend fun getCachedRepositories(): List<Repository>
    suspend fun getRemoteRepositories(): List<Repository>
}