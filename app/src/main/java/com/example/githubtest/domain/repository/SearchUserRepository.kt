package com.example.githubtest.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubtest.data.db.AppDatabase
import com.example.githubtest.data.db.SearchUserDao
import com.example.githubtest.data.remote.SearchUserRemoteDataSource
import com.example.githubtest.data.remote.SearchUserRemoteMediator
import com.example.githubtest.domain.model.SearchUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.Executors
import javax.inject.Inject

class SearchUserRepository @Inject constructor(
    private val remoteDataSource: SearchUserRemoteDataSource,
    private val dao: SearchUserDao,
    private val db: AppDatabase
) {


    @OptIn(ExperimentalPagingApi::class)
    fun fetchItems(q: String): Flow<PagingData<SearchUser>> = Pager(
        config = PagingConfig(pageSize = 30, prefetchDistance = 28, enablePlaceholders = false, initialLoadSize = 30),
        remoteMediator = SearchUserRemoteMediator(remoteDataSource, dao, db, q)
    ) {
        dao.getItems()
    }.flow

    fun getItem(id: Int) : Flow<SearchUser> = flow {
        emit(dao.getItemById(id))
    }.flowOn(Dispatchers.IO)

    fun updateFavorite(id: Int, isFavorite: Boolean) {
        Executors.newSingleThreadExecutor().submit {
            dao.updateFavorite(id, isFavorite)
        }
    }
}