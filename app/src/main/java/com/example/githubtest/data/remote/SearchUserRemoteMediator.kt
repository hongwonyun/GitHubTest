package com.example.githubtest.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.githubtest.data.db.AppDatabase
import com.example.githubtest.data.db.SearchUserDao
import com.example.githubtest.domain.model.SearchUser
import com.example.githubtest.domain.model.SearchUsersResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class SearchUserRemoteMediator @Inject constructor(
    private val remoteDataSource: SearchUserRemoteDataSource,
    private val dao: SearchUserDao,
    private val db: AppDatabase,
    private val q: String
) : RemoteMediator<Int, SearchUser>() {

    var page = 0

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchUser>
    ): MediatorResult {
        try {
            Log.d("TEST", "loadType $loadType")
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> null
                LoadType.APPEND -> {
                    page = page.plus(1)
                    page
                }
            } ?: return MediatorResult.Success(endOfPaginationReached = true)
            Log.d("TEST", "loadKey $loadKey")

            val apiResponse: SearchUsersResponse = remoteDataSource.getSearchUser(q, loadKey)
            val endOfPaginationReached = apiResponse.items.isNullOrEmpty() || apiResponse.totalCount?.div(10) == loadKey

            Log.d("TEST", "endOfPaginationReached $endOfPaginationReached")
            if (!apiResponse.items.isNullOrEmpty()) {
                db.withTransaction {
                    dao.insertAll(apiResponse.items)
                }
            } else {
                return MediatorResult.Error(Throwable("Error"))
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

}