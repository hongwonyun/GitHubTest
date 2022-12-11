package com.example.githubtest.di

import com.example.githubtest.domain.model.SearchUsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") q: String,
        @Query("page") page: Int
    ): SearchUsersResponse

}