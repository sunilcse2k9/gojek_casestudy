package com.gojek.casestudy.repository

import android.content.Context
import com.gojek.casestudy.model.GitHubRepository
import com.gojek.casestudy.network.NetworkApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class DataRepository(
    context: Context,
    private val expiryTimeInMins: Int,
    private val api: NetworkApi,
    private val sortingLogic: SortingLogic
): Repository {

    companion object {
        const val PACKAGE_NAME = "com.gojek.casestudy"
        private const val KEY_REPOSITORIES = "repositories"
        private const val KEY_TIME_STAMP = "time_stamp"
    }

    private val sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    override suspend fun getRepositories(isInternetConnected: Boolean): List<GitHubRepository> {
        return if (isInternetConnected) {
            getRemoteRepositories()
        } else {
            getCachedRepositories()
        }
    }

    private suspend fun getCachedRepositories(): List<GitHubRepository> {
        return withContext(Dispatchers.IO) {
            val ts = sharedPreferences.getLong(KEY_TIME_STAMP, 0)

            if (ts == 0L) {
                arrayListOf<GitHubRepository>()
            } else {
                val json = sharedPreferences.getString(KEY_REPOSITORIES, "[]")
                val type = object : TypeToken<List<GitHubRepository>>() {}.type
                gson.fromJson(json, type)
            }
        }
    }

    private suspend fun getRemoteRepositories(): List<GitHubRepository> {
        return try {
            withContext(Dispatchers.IO) {
                val repositories = api.getRepositoriesList()
                updateCache(repositories)
                repositories
            }
        } catch (e: Exception) {
            listOf()
        }
    }

    private fun updateCache(gitHubRepositories: List<GitHubRepository>) {
        if (gitHubRepositories.isNotEmpty()) {
            val ts = sharedPreferences.getLong(KEY_TIME_STAMP, 0)
            val ct = Calendar.getInstance().timeInMillis

            if (ct - ts >= expiryTimeInMins*60*1000) {
                sharedPreferences.edit().putString(KEY_REPOSITORIES, gson.toJson(gitHubRepositories))
                    .apply()
                sharedPreferences.edit()
                    .putLong(KEY_TIME_STAMP, Calendar.getInstance().timeInMillis)
                    .apply()
            }
        }
    }

    override suspend fun sortByNames(gitHubRepositories: List<GitHubRepository>): List<GitHubRepository> = withContext(Dispatchers.IO) { sortingLogic.sortByNames(gitHubRepositories) }
    override suspend fun sortByStars(gitHubRepositories: List<GitHubRepository>): List<GitHubRepository> = withContext(Dispatchers.IO) { sortingLogic.sortByStars(gitHubRepositories) }
}