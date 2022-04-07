package com.pratik.github.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pratik.github.data.remote.api.GitHubService
import com.pratik.github.data.remote.dto.Root
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZe = 25

class GitHubCommitsPageDataSource constructor(
    private val service: GitHubService,
    private val owner: String,
    private val repo: String
) : PagingSource<Int, Root>() {

//
//    override fun loadInitial(
//        params: LoadInitialParams<Int>,
//        callback: LoadInitialCallback<Int, Root>) {
//
//        fetchData(1, params.requestedLoadSize) {
//            callback.onResult(it, null, 2)
//        }
//    }
//
//    private fun fetchData(page: Int, pageSize: Int, callback: (List<Root>) -> Unit) {
//        scope.launch(getJobErrorHandler()) {
//            val response : Result<List<Root>> = dataSource.fetchCommits(page, pageSize)
//            if (response.status == Status.SUCCESS) {
//                val results = response.data
//                results?.let { callback(it) }
//            } else if (response.status == Status.ERROR) {
//                callback(emptyList())
//                postError(response.message)
//            }
//        }
//    }
//
//    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
//        postError(e.message ?: e.toString())
//    }
//
//    private fun postError(message: String?) {
//        Log.e(LOG_TAG, "An error happened: $message")
//
//    }
//
//    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Root>) {
//        val page = params.key
//        fetchData(page, params.requestedLoadSize) {
//            callback.onResult(it, page - 1)
//        }
//
//    }
//
//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Root>) {
//        val page = params.key
//        fetchData(page,params.requestedLoadSize) {
//            callback.onResult(it, page + 1)
//        }
//    }


    override fun getRefreshKey(state: PagingState<Int, Root>): Int? {
        return state.anchorPosition?.let {
            anchorPosition -> state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Root> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getCommits(
                owner = owner,
                repo = repo,
                page = pageIndex,
                pageSize = params.loadSize
            )
            val commits = response.body() ?: emptyList()
            val nextKey =
                if (commits.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZe)
                }
            LoadResult.Page(
                data = commits,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}