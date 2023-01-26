package com.phalgundixit.githubtrending.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.phalgundixit.githubtrending.base.api.GithubApi
import com.phalgundixit.githubtrending.data.model.Repo
import retrofit2.HttpException
private const val STARTING_PAGE_INDEX = 1
class GithubPagingSource(
    private val githubApi: GithubApi,
    private val query: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = githubApi.getTrendingRepos(query, position, 10)
            val repos = response.repo

            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,//for paging forword and backword
                nextKey = if (repos.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return super.getRefreshKey(state)
    }
}