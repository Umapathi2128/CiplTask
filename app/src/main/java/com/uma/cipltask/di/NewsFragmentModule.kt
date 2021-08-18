package com.uma.cipltask.di

import com.uma.cipltask.ui.news.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment() : NewsFragment
}