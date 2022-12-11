package com.example.githubtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
data class SearchUsersResponse(
	val totalCount: Int? = null,
	val incompleteResults: Boolean? = null,
	val items: List<SearchUser?>? = null
)

@Serializable
@Entity(tableName = "SearchUser")
data class SearchUser(
	val login: String? = null,
	@PrimaryKey
	val id: Int? = null,
	val nodeId: String? = null,
	val avatarUrl: String? = null,
	val gravatarId: String? = null,
	val url: String? = null,
	val htmlUrl: String? = null,
	val followersUrl: String? = null,
	val followingUrl: String? = null,
	val gistsUrl: String? = null,
	val starredUrl: String? = null,
	val subscriptionsUrl: String? = null,
	val organizationsUrl: String? = null,
	val reposUrl: String? = null,
	val eventsUrl: String? = null,
	val receivedEventsUrl: String? = null,
	val type: String? = null,
	val siteAdmin: Boolean? = null,
	val score: Double? = null,
	var isFavorite: Boolean = false
)

