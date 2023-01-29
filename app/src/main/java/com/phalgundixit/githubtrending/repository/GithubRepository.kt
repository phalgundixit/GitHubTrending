package com.phalgundixit.githubtrending.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.phalgundixit.githubtrending.data.model.ContributorsResponse
import com.phalgundixit.githubtrending.data.model.Repo

interface GithubRepository {
    fun getSearchResults(query: String):LiveData<PagingData<Repo>>
    suspend fun getContributors(query: String):ArrayList<ContributorsResponse>
    suspend fun getRepoDetails(query: String):ArrayList<Repo>
}