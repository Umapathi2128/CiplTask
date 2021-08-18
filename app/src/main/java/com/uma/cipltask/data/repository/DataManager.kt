package com.uma.cipltask.data.repository

import android.content.Context
import com.uma.cipltask.data.model.GitRepoResponse
import com.uma.cipltask.data.network.ApiHelper
import retrofit2.Response
import javax.inject.Inject

class DataManager @Inject constructor(
    val apiHelper: ApiHelper
) : DataHelper {
    override suspend fun getRepoList(page: String, per_page: String): Response<GitRepoResponse> = apiHelper.getRepoApi(page, per_page)

    override suspend fun getNewsList(country: String, category: String) =
        apiHelper.getNewsList(country, category)
}