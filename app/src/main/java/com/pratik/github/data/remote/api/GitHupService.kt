package com.pratik.github.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHupService {

    companion object {
        const val END_POINT = "https://api.github.com"
    }

    @GET("/repos/{owner}/{repo}/commits/")
    suspend fun getCommits(
        @Path("owner") owner : String,
        @Path("repo") repo : String,
        @Query("page") page: Int,
        @Query("per_page") pageSize : Int
    )

}