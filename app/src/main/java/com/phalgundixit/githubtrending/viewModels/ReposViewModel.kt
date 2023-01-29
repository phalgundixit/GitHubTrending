package com.phalgundixit.githubtrending.viewModels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.phalgundixit.githubtrending.base.api.GithubApi
import com.phalgundixit.githubtrending.data.model.ContributorsResponse
import com.phalgundixit.githubtrending.data.model.Repo
import com.phalgundixit.githubtrending.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val contributorListLiveData = MutableLiveData<ArrayList<ContributorsResponse>>()
    private val repoDetailsListLiveData = MutableLiveData<ArrayList<Repo>>()

    fun getContributorListLiveData() = contributorListLiveData
    fun getRepoDetailsListLiveData() = repoDetailsListLiveData

    val repos: LiveData<PagingData<Repo>> = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString)
    }.cachedIn(viewModelScope)

    fun searchRepos(query: String) {
        currentQuery.value = query
    }

    suspend fun getContributors(query: String) {
        val response = repository.getContributors(query)
        contributorListLiveData.postValue(response)
    }
    suspend fun getRepoDetails(query: String) {
        val response = repository.getRepoDetails(query)
        repoDetailsListLiveData.postValue(response)
    }

    companion object {
        private const val DEFAULT_QUERY = "Q"
    }
}