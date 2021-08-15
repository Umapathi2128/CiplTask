package com.uma.cipltask.ui.home.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.uma.cipltask.data.model.GitRepoResponseItem
import com.uma.cipltask.data.network.ApiService
import com.uma.cipltask.data.repository.DataManager

class GitRepoDataSource(private val dataManager: DataManager) : PagingSource<Int, GitRepoResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitRepoResponseItem> {
        return try {
            val currentLoadingPageKey = params.key ?: 1
            val response = dataManager.getRepoList(currentLoadingPageKey.toString(),"10")
            val responseData = mutableListOf<GitRepoResponseItem>()
            val data = response.body() ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                responseData,
                prevKey = null,
                nextKey = if (currentLoadingPageKey < response.body()?.size!!) currentLoadingPageKey + 10 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitRepoResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}