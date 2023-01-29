package com.phalgundixit.githubtrending.base.api

import com.phalgundixit.githubtrending.data.model.ContributorsResponse
import com.phalgundixit.githubtrending.data.model.Repo
import com.phalgundixit.githubtrending.data.model.TrendingRepoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubApi {

    @GET("search/repositories?sort=stars")
    suspend fun getTrendingRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): TrendingRepoResponse

    @GET()
    suspend fun getContributors(
        @Url() query: String,
    ): ArrayList<ContributorsResponse>

    @GET()
    suspend fun getRepoDetails(
        @Url() query: String,
    ): ArrayList<Repo>
}