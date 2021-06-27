package com.gojek.casestudy.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
	@SerializedName("name")
	val name: String,
	@SerializedName("description")
	val description: String,
	@SerializedName("owner")
	val owner: Owner,
	@SerializedName("language")
	val language: String,
	@SerializedName("watchers_count")
	val watchersCount: Int,
	@SerializedName("forks_count")
	val forksCount: Int
): Parcelable {
	var itemSelected: Boolean = false

	@Parcelize
	data class Owner(
		@SerializedName("avatar_url")
		val avatarUrl: String,
		@SerializedName("html_url")
		val htmlUrl: String
	): Parcelable
}