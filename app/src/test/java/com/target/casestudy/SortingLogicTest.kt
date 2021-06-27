package com.target.casestudy

import com.gojek.casestudy.model.GitHubRepository
import com.gojek.casestudy.repository.SortingLogic
import org.junit.Before
import org.junit.Test

class SortingLogicTest {
    private val repositories = mutableListOf<GitHubRepository>()
    private val sortingLogic = SortingLogic()

    @Before
    fun setup() {
        repositories.add(
            GitHubRepository(
                "xyz",
                "Test repo",
                GitHubRepository.Owner("", ""),
                "C++",
            200,
            200)
        )

        repositories.add(
            GitHubRepository(
                "abc",
                "Test repo",
                GitHubRepository.Owner("", ""),
                "C++",
                100,
                200)
        )
    }

    @Test
    fun validateSortingByName_SizeCheck() {
        val sorted = sortingLogic.sortByNames(repositories)
        assert(sorted.size == repositories.size)
    }

    @Test
    fun validateSortingByName() {
        assert(repositories[0].name == "xyz")
        val sorted = sortingLogic.sortByNames(repositories)
        assert(sorted[0].name == "abc")
    }

    @Test
    fun validateSortingByStars_SizeCheck() {
        val sorted = sortingLogic.sortByNames(repositories)
        assert(sorted.size == repositories.size)
    }

    @Test
    fun validateSortingByStars() {
        assert(repositories[0].watchersCount == 200)
        val sorted = sortingLogic.sortByStars(repositories)
        assert(sorted[0].watchersCount == 100)
    }
}