package com.gojek.casestudy.repository

import com.gojek.casestudy.model.GitHubRepository

class SortingLogic {
    fun sortByNames(gitHubRepositories: List<GitHubRepository>) = gitHubRepositories.sortedBy { it.name }
    fun sortByStars(gitHubRepositories: List<GitHubRepository>) = gitHubRepositories.sortedBy { it.watchersCount }
}