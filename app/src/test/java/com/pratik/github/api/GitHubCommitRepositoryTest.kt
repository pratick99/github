package com.pratik.github.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pratik.github.data.remote.api.GitHubService
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSource
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
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit

@RunWith(JUnit4::class)
class GitHubCommitRepositoryTest {

    private lateinit var repository: CommitRepository
    private val service = mock(GitHubService::class.java)
    private val remoteDataSource = GitHubRemoteDataSource(service)

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