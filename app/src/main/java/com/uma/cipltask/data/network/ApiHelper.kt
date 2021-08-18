package com.uma.cipltask.data.network

import javax.inject.Inject

class ApiHelper @Inject constructor(var apiService: ApiService,var gitApi : GitApiService) {


//    private fun getGitApi() =
//        getGitApiRetrofit().create(ApiService::class.java)
//
//    private fun getNewsApi() = getNewsListApiRetrofit().create(ApiService::class.java)

    suspend fun getNewsList(country: String, category: String) =
        apiService.getNewsList(country, category)

    suspend fun getRepoApi(page: String, per_page: String) =
        gitApi.getRepoList(page, per_page)
}