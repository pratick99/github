package com.pratik.github.data.remote.datasource

import androidx.paging.PagingData
import com.pratik.github.data.remote.dto.Root
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GitHubRemoteDataSource {

    suspend fun fetchCommits(owner: String, repo: String): Flow<PagingData<Root>>

    suspend fun getCommit(commitSha: String) : Response<Root>
}