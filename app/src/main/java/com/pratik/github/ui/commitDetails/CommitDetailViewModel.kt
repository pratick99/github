package com.pratik.github.ui.commitDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratik.github.repository.CommitRepository
import com.pratik.github.ui.util.CommitViewState
import com.pratik.github.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommitDetailViewModel @Inject constructor(
    private val commitRepository: CommitRepository,
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        viewStateLiveData.postValue(CommitViewState.Error(message = throwable.localizedMessage))
    }

    private val viewStateLiveData = MutableLiveData<CommitViewState>()

    fun getCommitViewStateLiveData(): LiveData<CommitViewState> = viewStateLiveData

    fun getCommitDetail(sha: String) {
        viewStateLiveData.postValue(CommitViewState.Loading)
        viewModelScope.launch(handler)  {
            val result = commitRepository.getCommit(commitSha = sha)
//            if (result.status == Result.Status.SUCCESS) {
//                result.data?.let {
//                    viewStateLiveData.postValue(CommitViewState.Success(result.data))
//                }
//            } else if (result.status == Result.Status.ERROR) {
//                result.message?.let {
//                    viewStateLiveData.postValue(CommitViewState.Error(result.message))
//                }
//            }
            if(result.isSuccessful) {
                val commitDetail = result.body()
                commitDetail?.let {
                    viewStateLiveData.postValue(CommitViewState.Success(it))
                }
            } else {
                val error = result.errorBody()
                error?.let {
                    viewStateLiveData.postValue(CommitViewState.Error(it.toString()))
                }
            }
        }
    }
}
