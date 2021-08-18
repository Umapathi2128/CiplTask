package com.uma.cipltask.data.network

import com.uma.cipltask.data.model.GitRepoResponse
import com.uma.cipltask.data.model.NewsListResponse
import com.uma.cipltask.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GitApiService {

    @GET("repos")
    suspend fun getRepoList(
        @Query("page") page: String,
        @Query("per_page") per_page: String
    ): Response<GitRepoResponse>

}