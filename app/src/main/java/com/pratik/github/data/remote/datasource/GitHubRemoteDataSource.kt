package com.pratik.github.data.remote.datasource

import com.pratik.github.data.remote.api.GitHupService
import com.pratik.github.util.BaseDataSource
import javax.inject.Inject

class GitHubRemoteDataSource @Inject constructor(private val gitHupService: GitHupService) :
    BaseDataSource() {

    suspend fun fetchCommits(page: Int, pageSize: Int) =
        getResult { gitHupService.getCommits("palantir", "blueprint", page, pageSize) }

}