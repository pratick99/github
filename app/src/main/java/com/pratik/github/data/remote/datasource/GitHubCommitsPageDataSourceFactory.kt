package com.pratik.github.data.remote.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.pratik.github.data.remote.dto.Root
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GitHubCommitsPageDataSourceFactory @Inject constructor(private val dataSource: GitHubRemoteDataSource,
                                                             private val scope: CoroutineScope) : DataSource.Factory<Int, Root>() {

    private val liveData = MutableLiveData<GitHubCommitsPageDataSource>()

    override fun create(): DataSource<Int, Root> {
        val source = GitHubCommitsPageDataSource(dataSource,scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

}