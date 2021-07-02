package com.pratik.github.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.repository.CommitRepository
import com.pratik.github.ui.commitDetails.CommitDetailViewModel
import com.pratik.github.ui.util.CommitViewState
import com.pratik.github.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class CommitDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

    private lateinit var viewModel: CommitDetailViewModel

    @Mock
    private lateinit var commitRepository: CommitRepository

    @Mock
    lateinit var viewStateObserver: Observer<CommitViewState>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = CommitDetailViewModel(commitRepository, coroutineScope)
    }

    @Test
    fun `should success when fetch from server returns dummy data`() {
        runBlocking {
            val data = Root("", "", null, "", "", "", null, null)
            doReturn(Result.success(data)).`when`(commitRepository).getCommit("")
            viewModel.getCommitDetail("")
            viewModel.getCommitViewStateLiveData().observeForever(viewStateObserver)
            verify(viewStateObserver).onChanged(CommitViewState.Success(data = data))
            viewModel.getCommitViewStateLiveData().removeObserver(viewStateObserver)
        }
    }

    @Test
    fun `on network request invoke with no internet connection return error`() {
        runBlocking {
            val error = "Unknown Host, Please connect to a proper host"
            doReturn(Result.error(error, null)).`when`(commitRepository).getCommit("")
            viewModel.getCommitDetail("")
            viewModel.getCommitViewStateLiveData().observeForever(viewStateObserver)
            verify(viewStateObserver).onChanged(CommitViewState.Error(error))
            viewModel.getCommitViewStateLiveData().removeObserver(viewStateObserver)
        }
    }
}