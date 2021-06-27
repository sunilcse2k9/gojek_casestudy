package com.gojek.casestudy.network.repository

import com.gojek.casestudy.model.Repository

class SortingLogic {
    fun sortByNames(repositories: List<Repository>) = repositories.sortedBy { it.name }
    fun sortByStars(repositories: List<Repository>) = repositories.sortedBy { it.watchersCount }
}