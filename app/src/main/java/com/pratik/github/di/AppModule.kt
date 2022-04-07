package com.pratik.github.di

import com.pratik.github.data.remote.api.GitHubService
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSource
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSourceImpl
import com.pratik.github.repository.CommitRepository
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): GitHubService = retrofit.create(GitHubService::class.java)


    @Provides
    @Singleton
    fun provideCommitRepository(remoteDataSource: GitHubRemoteDataSource): CommitRepository {
     return CommitRepository(remoteDataSource)
    }
}
