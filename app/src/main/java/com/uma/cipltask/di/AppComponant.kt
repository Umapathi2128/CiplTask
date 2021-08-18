package com.uma.cipltask.di

import com.uma.cipltask.MyApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        HomeFragmentModule::class,
        NewsFragmentModule::class,
        AppModule::class]
)
interface AppComponant {

    fun inject(application: MyApplication)
}