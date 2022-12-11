package com.example.githubtest.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubtest.domain.model.SearchUser
import com.example.githubtest.domain.model.SearchUsersResponse

@Database(entities = [SearchUser::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "gitHubTest.db"
        const val VERSION = 1
    }

    abstract fun searchUserDao(): SearchUserDao
}