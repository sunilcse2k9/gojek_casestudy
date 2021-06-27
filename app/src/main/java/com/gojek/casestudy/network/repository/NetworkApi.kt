package com.gojek.casestudy.network.repository

import com.gojek.casestudy.model.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("/orgs/Octokit/repos")
    suspend fun getRepositoriesList(): List<Repository>
}