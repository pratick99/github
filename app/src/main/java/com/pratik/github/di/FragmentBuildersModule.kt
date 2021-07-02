package com.pratik.github.di

import com.pratik.github.ui.commitDetails.CommitDetailFragment
import com.pratik.github.ui.commitlist.CommitListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCommitListFragment(): CommitListFragment

    @ContributesAndroidInjector
    abstract fun contributeCommitDetailsFragment() : CommitDetailFragment
}
