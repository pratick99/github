package com.pratik.github.ui.commits

import androidx.lifecycle.ViewModel
import com.pratik.github.di.CoroutineScopeIO
import com.pratik.github.ui.repository.CommitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class CommitListViewModel @Inject constructor(
    private val repository: CommitRepository,
    @CoroutineScopeIO private val scope: CoroutineScope
) : ViewModel() {

    val commitList by lazy {
        repository.observeRemotePagedSets(scope)
    }


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}