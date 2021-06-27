package com.gojek.casestudy.network.repository

import com.gojek.casestudy.model.GitHubRepository
import retrofit2.http.GET

interface NetworkApi {
    @GET("/orgs/Octokit/repos")
    suspend fun getRepositoriesList(): List<GitHubRepository>
}