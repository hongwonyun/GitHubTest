package com.example.githubtest.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.githubtest.data.db.AppDatabase
import com.example.githubtest.data.db.SearchUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }


    @Provides
    @Singleton
    fun provideSearchUserDao(appDatabase: AppDatabase): SearchUserDao {
        return appDatabase.searchUserDao()
    }
}