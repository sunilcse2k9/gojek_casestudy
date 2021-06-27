package com.gojek.casestudy.network.repository

import android.content.Context
import com.gojek.casestudy.model.Repository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class DataRepository(
    context: Context,
    private val expiryTimeInMins: Int,
    private val api: NetworkApi,
    private val sortingLogic: SortingLogic
): com.gojek.casestudy.network.repository.Repository {

    companion object {
        private const val PACKAGE_NAME = "com.gojek.casestudy"
        private const val KEY_REPOSITORIES = "repositories"
        private const val KEY_TIME_STAMP = "time_stamp"
    }

    private val sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    override suspend fun getCachedRepositories(): List<Repository> {
        val ts = sharedPreferences.getLong(KEY_TIME_STAMP, 0)

        if (ts == 0L) {
            return arrayListOf()
        } else {
            val json = sharedPreferences.getString(KEY_REPOSITORIES, "[]")
            val type = object : TypeToken<List<Repository>>() {}.type
            return gson.fromJson(json, type)
        }
    }

    override suspend fun getRemoteRepositories(): List<Repository> =
        withContext(Dispatchers.IO) {
            val repositories = api.getRepositoriesList()
            updateCache(repositories)
            repositories
        }

    private fun updateCache(repositories: List<Repository>) {
        if (repositories.isNotEmpty()) {
            val ts = sharedPreferences.getLong(KEY_TIME_STAMP, 0)
            val ct = Calendar.getInstance().timeInMillis

            if (ct - ts >= expiryTimeInMins*60*1000) {
                sharedPreferences.edit().putString(KEY_REPOSITORIES, gson.toJson(repositories))
                    .apply()
                sharedPreferences.edit()
                    .putLong(KEY_TIME_STAMP, Calendar.getInstance().timeInMillis)
                    .apply()
            }
        }
    }

    override suspend fun sortByNames(repositories: List<Repository>): List<Repository> = withContext(Dispatchers.IO) { sortingLogic.sortByNames(repositories) }

    override suspend fun sortByStars(repositories: List<Repository>): List<Repository> = withContext(Dispatchers.IO) { sortingLogic.sortByStars(repositories) }
}