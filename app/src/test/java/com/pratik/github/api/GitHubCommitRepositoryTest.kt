package com.pratik.github.api

import android.provider.DocumentsContract
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pratik.github.data.remote.api.GitHupService
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSource
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.ui.repository.CommitRepository
import junit.framework.Assert.assertNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class GitHubCommitRepositoryTest {

    private lateinit var repository: CommitRepository
    private val service = mock(GitHupService::class.java)
    private val remoteDataSource = GitHubRemoteDataSource(service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        repository = CommitRepository(remoteDataSource)
    }

    @Test
    fun loadCommitsFromNetwork() {
        runBlocking {
            val pagedList = repository.observeRemotePagedSets(coroutineScope).value ?: emptyList<Root>()
            assertThat(pagedList, isNull())
        }
    }
}