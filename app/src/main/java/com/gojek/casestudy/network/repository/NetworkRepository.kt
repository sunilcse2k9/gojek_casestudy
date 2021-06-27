package com.gojek.casestudy.network.repository

import android.content.Context
import com.gojek.casestudy.model.Repository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.util.*

class NetworkRepository(context: Context, private val expiryTimeInMins: Int, private val api: NetworkApi): DataRepository {

    companion object {
        private const val PACKAGE_NAME = "com.gojek.casestudy"
        private const val KEY_REPOSITORIES = "repositories"
        private const val KEY_TIME_STAMP = "time_stamp"
    }

    private val sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE)

    override suspend fun getCachedRepositories(): List<Repository> {
        val ts = sharedPreferences.getLong(KEY_TIME_STAMP, 0)
        val ct = Calendar.getInstance().timeInMillis

        if (ts == 0L) {
            getRemoteRepositories()
            return arrayListOf()
        } else {
            if (ct - ts >= expiryTimeInMins*60*1000) {
                getRemoteRepositories()
            }

            val json = sharedPreferences.getString(KEY_REPOSITORIES, "[]")
            val gson = Gson()
            val type = object : TypeToken<List<Repository>>() {}.type
            return gson.fromJson(json, type)
        }
    }

    override suspend fun getRemoteRepositories(): List<Repository> =
        withContext(Dispatchers.IO) {
            val repositories = api.getRepositoriesList()
            updateRepositories(repositories)
            repositories
        }

    private suspend fun updateRepositories(repositories: List<Repository>) {
        if (repositories.isNotEmpty()) {
            val gson = Gson()
            //withContext(Dispatchers.Main) {
            sharedPreferences.edit().putString(KEY_REPOSITORIES, gson.toJson(repositories))
                .apply()
            sharedPreferences.edit().putLong(KEY_TIME_STAMP, Calendar.getInstance().timeInMillis)
                .apply()
            //}
        }
    }


}