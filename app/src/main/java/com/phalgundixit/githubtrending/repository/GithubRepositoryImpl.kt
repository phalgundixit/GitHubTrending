package com.phalgundixit.githubtrending.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.phalgundixit.githubtrending.base.api.GithubApi

import com.phalgundixit.githubtrending.data.GithubPagingSource
import com.phalgundixit.githubtrending.repository.GithubRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(private val githubApi: GithubApi) :
    GithubRepository {

    override fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 1000,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(githubApi, query) }
        ).liveData
}