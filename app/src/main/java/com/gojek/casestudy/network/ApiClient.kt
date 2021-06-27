package com.gojek.casestudy.network


import com.gojek.casestudy.network.repository.NetworkApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    private const val API_BASE_URL = "https://api.github.com"

    fun build(): NetworkApi {
        val requestInterface = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return requestInterface.create(NetworkApi::class.java)
    }
}