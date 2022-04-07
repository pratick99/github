package com.pratik.github.ui.commitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.repository.CommitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CommitListViewModel @Inject constructor(private val repository: CommitRepository) : ViewModel() {

    suspend fun getCommits(): Flow<PagingData<Root>> {
        return repository.fetchCommits("", "").cachedIn(viewModelScope)
    }
}
