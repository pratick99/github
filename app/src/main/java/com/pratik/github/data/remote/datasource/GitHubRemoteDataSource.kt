package com.pratik.github.data.remote.datasource

import com.pratik.github.data.remote.api.GitHubService
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.util.BaseDataSource
import com.pratik.github.util.Result
import javax.inject.Inject

class GitHubRemoteDataSource @Inject constructor(private val gitHubService: GitHubService) :
    BaseDataSource() {

    suspend fun fetchCommits(page: Int, pageSize: Int) :Result<List<Root>> =
        getResult { gitHubService.getCommits("palantir", "blueprint", page, pageSize) }

    suspend fun getCommit(commitSha: String)= getResult { gitHubService.getCommitDetails(commitSha = commitSha, owner = "palantir", repo = "blueprint") }

}