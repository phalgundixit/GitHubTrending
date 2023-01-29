package com.phalgundixit.githubtrending.viewModels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.phalgundixit.githubtrending.data.model.ContributorsResponse
import com.phalgundixit.githubtrending.data.model.Repo
import com.phalgundixit.githubtrending.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        CoroutineScope(Dispatchers.IO).launch(handler) {
            val response = repository.getContributors(query)
            contributorListLiveData.postValue(response)
        }
    }

    suspend fun getRepoDetails(query: String) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            val response = repository.getRepoDetails(query)
            repoDetailsListLiveData.postValue(response)
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "Q"
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Network", "Caught $exception")
    }
}