package com.pratik.github.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pratik.github.data.remote.api.GitHubService
import com.pratik.github.data.remote.dto.Root
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GitHubRemoteDataSourceImpl @Inject constructor(private val gitHubService: GitHubService) :
    GitHubRemoteDataSource {

    override suspend fun getCommit(commitSha: String): Response<Root> {
        return gitHubService.getCommitDetails(commitSha = commitSha, owner = "palantir", repo = "blueprint")
    }

    override suspend fun fetchCommits(
        owner: String,
        repo: String
    ): Flow<PagingData<Root>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZe, enablePlaceholders = false),
            pagingSourceFactory = {
                GitHubCommitsPageDataSource(service = gitHubService, owner, repo)
            }
        ).flow
    }
}
