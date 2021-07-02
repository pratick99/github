package com.pratik.github.ui.util

import com.pratik.github.data.remote.dto.Root

sealed class CommitViewState {
    object Loading : CommitViewState()
    data class Error(val message: String?) : CommitViewState()
    data class Success(val data: Root) : CommitViewState()
}
