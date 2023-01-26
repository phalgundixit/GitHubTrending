package com.phalgundixit.githubtrending.base.api

import com.phalgundixit.githubtrending.data.model.TrendingRepoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApi {

    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer --Enter Your Git Key Here",
        "X-GitHub-Api-Version: 2022-11-28"

    )
    @GET("search/repositories?sort=stars")
    suspend fun getTrendingRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): TrendingRepoResponse
}