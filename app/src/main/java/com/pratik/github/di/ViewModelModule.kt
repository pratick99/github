package com.pratik.github.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratik.github.ui.commitDetails.CommitDetailViewModel
import com.pratik.github.ui.commitlist.CommitListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CommitListViewModel::class)
    abstract fun bindCommitsViewModel(viewModel: CommitListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommitDetailViewModel::class)
    abstract fun bindCommitDetailViewMode(viewModel: CommitDetailViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}