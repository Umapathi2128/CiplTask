package com.uma.cipltask.di

import android.content.Context
import com.uma.cipltask.data.network.ApiService
import com.uma.cipltask.data.network.GitApiService
import com.uma.cipltask.data.network.RetrofitBuilder
import com.uma.cipltask.ui.news.adapter.NewsListAdapter
import com.uma.cipltask.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context : Context) {

    @Singleton
    @Provides
    fun provideNewsApi(retrofitBuilder: RetrofitBuilder) = retrofitBuilder.getNewsListApiRetrofit().create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideGitApi(retrofitBuilder: RetrofitBuilder) = retrofitBuilder.getGitApiRetrofit().create(GitApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkHelper() = NetworkHelper(context)

    @Singleton
    @Provides
    fun provideNewsAdapter() = NewsListAdapter()
}