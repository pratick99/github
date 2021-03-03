package com.pratik.github.data.remote.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.util.Result.Status
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GitHubCommitsPageDataSource @Inject constructor(
    private val dataSource: GitHubRemoteDataSource,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Root>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Root>) {

        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<Root>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchCommits(page, pageSize)
            if (response.status == Status.SUCCESS) {
                val results = response.data
                results?.let { callback(it) }
            } else if (response.status == Status.ERROR) {
                callback(emptyList())
                postError(response.message)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String?) {
        Log.e(LOG_TAG, "An error happened: $message")

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Root>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Root>) {
        val page = params.key
        fetchData(page,params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    companion object {
        private val LOG_TAG = GitHubRemoteDataSource::class.java.simpleName
    }
}