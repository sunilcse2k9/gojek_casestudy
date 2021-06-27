package com.gojek.casestudy.model

sealed class Resource {
    data class Success(val data: List<GitHubRepository>): Resource()
    data class Error(val message: String): Resource()
    object Loading : Resource()
}
