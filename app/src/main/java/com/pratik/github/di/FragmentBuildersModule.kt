package com.pratik.github.di

import com.pratik.github.ui.commits.CommitListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCommitListFragment(): CommitListFragment
}
