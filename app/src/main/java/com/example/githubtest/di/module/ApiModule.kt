package com.example.githubtest.di.module

import com.example.githubtest.BuildConfig
import com.example.githubtest.data.db.AppDatabase
import com.example.githubtest.data.db.SearchUserDao
import com.example.githubtest.data.remote.SearchUserRemoteDataSource
import com.example.githubtest.di.ApiService
import com.example.githubtest.domain.repository.SearchUserRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val TIME_OUT_SECONDS = 10

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
        coerceInputValues = true
        encodeDefaults = false
        classDiscriminator = "#class"
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideSearchUserRepository(
        remoteDataSource: SearchUserRemoteDataSource,
        dao: SearchUserDao,
        db: AppDatabase
    ): SearchUserRepository {
        return SearchUserRepository(remoteDataSource, dao, db)
    }

    @Provides
    fun provideSearchUserRemoteDataSource(
        apiService: ApiService
    ): SearchUserRemoteDataSource {
        return SearchUserRemoteDataSource(apiService)
    }
}