package com.pratik.github.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pratik.github.data.remote.api.GitHubService
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSourceImpl
import com.pratik.github.repository.CommitRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class GitHubCommitRepositoryTest {

    private lateinit var repository: CommitRepository
    private val service = mock(GitHubService::class.java)
    private val remoteDataSource = GitHubRemoteDataSourceImpl(service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    @Before
    fun init() {
        repository = CommitRepository(remoteDataSource)
    }

    @Test
    fun loadCommitsFromNetwork() {
        runBlocking {
            val pagedList =
                repository.observeRemotePagedSets(coroutineScope).value
            assertTrue(pagedList.isNullOrEmpty())
        }
    }
}