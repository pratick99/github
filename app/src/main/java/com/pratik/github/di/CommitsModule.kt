package com.pratik.github.di

import com.pratik.github.data.remote.datasource.GitHubRemoteDataSource
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommitsModule {

    @Binds
    abstract fun bindsCommitsDataSource(gitHubRemoteDataSourceImpl: GitHubRemoteDataSourceImpl) : GitHubRemoteDataSource


}