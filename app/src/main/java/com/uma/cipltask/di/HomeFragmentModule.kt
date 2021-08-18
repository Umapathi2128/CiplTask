package com.uma.cipltask.di

import com.uma.cipltask.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment() : HomeFragment
}