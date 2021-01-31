package com.pratik.github.ui.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSource
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.data.remote.datasource.GitHubCommitsPageDataSourceFactory
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommitRepository @Inject constructor(private val remoteDataSource: GitHubRemoteDataSource) {

    fun observeRemotePagedSets(scope: CoroutineScope): LiveData<PagedList<Root>> {
        val dataSourceFactory = GitHubCommitsPageDataSourceFactory(remoteDataSource, scope)
        return LivePagedListBuilder(dataSourceFactory, GitHubCommitsPageDataSourceFactory.pagedListConfig()).build()
    }
}