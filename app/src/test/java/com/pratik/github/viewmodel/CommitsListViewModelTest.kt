package com.pratik.github.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pratik.github.data.remote.datasource.GitHubRemoteDataSource
import com.pratik.github.ui.commits.CommitListViewModel
import com.pratik.github.ui.repository.CommitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class CommitsListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private lateinit var repository : CommitRepository

    private val viewModel = mock(CommitListViewModel::class.java)


    @Before
    fun setup() {
        repository = mock(CommitRepository::class.java)
    }

    @Test
    fun testNull() {
        verify(viewModel, never())
    }

    @Test
    fun doNotFetchWithoutObservers() {
        verify(repository, never()).observeRemotePagedSets(coroutineScope)
    }
}