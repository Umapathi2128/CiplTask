package com.uma.cipltask.data.network

class ApiHelper : RetrofitBuilder() {


    private fun getGitApi() =
        getGitApiRetrofit().create(ApiService::class.java)

    private fun getNewsApi() = getNewsListApiRetrofit().create(ApiService::class.java)

    suspend fun getNewsList(country: String, category: String) =
        getNewsApi().getNewsList(country, category)

    suspend fun getRepoApi(page: String, per_page: String) =
        getGitApi().getRepoList(page, per_page)
}