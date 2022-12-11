package com.example.githubtest.data.remote

import com.example.githubtest.di.ApiService
import com.example.githubtest.domain.model.SearchUsersResponse
import javax.inject.Inject

class SearchUserRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getSearchUser(q: String, page: Int): SearchUsersResponse = apiService.getSearchUsers(q, page)
}