package com.example.githubtest.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubtest.domain.model.SearchUser
import com.example.githubtest.domain.model.SearchUsersResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SearchUser?>)

    @Query("SELECT * FROM SearchUser WHERE isFavorite = 0")
    fun getItems(): PagingSource<Int, SearchUser>

    @Query("SELECT * FROM SearchUser WHERE isFavorite = 1")
    fun getFavoriteItems(): Flow<List<SearchUser>>

    @Query("DELETE FROM SearchUser")
    suspend fun clearItems()

    @Query("UPDATE SearchUser SET isFavorite = :isFavorite WHERE id = :id")
    fun updateFavorite(id: Int, isFavorite: Boolean)


    @Query("SELECT * FROM SearchUser WHERE id = :id ")
    fun getItemById(id: Int) : SearchUser
}