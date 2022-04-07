package com.pratik.github.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSource
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSourceImpl
import com.pratik.github.data.remote.dto.Root
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class CommitRepository @Inject constructor(private val remoteDataSource: GitHubRemoteDataSource) {

    suspend fun fetchCommits(repo: String, owner: String) : Flow<PagingData<Root>> {
        return remoteDataSource.fetchCommits(owner = owner, repo = repo)
    }
    suspend fun getCommit(commitSha: String) = remoteDataSource.getCommit(commitSha = commitSha)
}
