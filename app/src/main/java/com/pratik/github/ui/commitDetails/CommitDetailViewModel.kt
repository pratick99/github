package com.pratik.github.ui.commitDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratik.github.di.CoroutineScopeIO
import com.pratik.github.repository.CommitRepository
import com.pratik.github.ui.util.CommitViewState
import com.pratik.github.util.Result
import kotlinx.coroutines.*
import javax.inject.Inject

class CommitDetailViewModel @Inject constructor(
    private val commitRepository: CommitRepository,
    @CoroutineScopeIO private val scope: CoroutineScope
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        viewStateLiveData.postValue(CommitViewState.Error(message = throwable.localizedMessage))
    }

    private val viewStateLiveData = MutableLiveData<CommitViewState>()

    fun getCommitViewStateLiveData(): LiveData<CommitViewState> = viewStateLiveData

    fun getCommitDetail(sha: String) {
        viewStateLiveData.postValue(CommitViewState.Loading)
        scope.launch(handler) {
            val result = commitRepository.getCommit(commitSha = sha)
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let {
                    viewStateLiveData.postValue(CommitViewState.Success(result.data))
                }
            } else if (result.status == Result.Status.ERROR) {
                result.message?.let {
                    viewStateLiveData.postValue(CommitViewState.Error(result.message))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
