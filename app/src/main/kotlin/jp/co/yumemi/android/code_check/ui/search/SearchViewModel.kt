/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.CoreApplication
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.data.model.GithubServerResponse
import jp.co.yumemi.android.code_check.repository.GithubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for managing the data and business logic related to the SearchFragment.
 * Uses dependency injection to retrieve an instance of [GithubRepository] for data retrieval.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    val githubRepository: GithubRepository
) : AndroidViewModel(application) {

    private val _githubRepositoryList = MutableLiveData<List<GithubRepositoryData>>(null)
    val gitHubRepositoryList: LiveData<List<GithubRepositoryData>> get() = _githubRepositoryList

    /**
     * Perform a search for GitHub repositories based on the provided input text.
     * Updates the [_githubRepositoryList] with the search results.
     *
     * @param inputText The text used for searching GitHub repositories.
     */
    fun searchResults(inputText: String) {
        Log.d("SearchViewModel", "Searching GitHub repositories with input: $inputText")
        // Call the getGitHubAccountFromDataSource function from the injected githubRepository
        viewModelScope.launch {
            try {
                val serverResponse: GithubServerResponse? =
                    githubRepository.getGitHubAccountFromDataSource(inputText)
                if (serverResponse != null) {
                    Log.d("SearchViewModel", "Search results received: ${serverResponse.items?.size} items")
                    // Update the _githubRepositoryList with the search results
                    _githubRepositoryList.value = serverResponse.items
                } else {
                    Log.e("SearchViewModel", "Search results are null or empty")
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error during search: ${e.message}")
            }
        }
}



    private fun hasInternetConnection():Boolean {

        val connectivityManager = getApplication<CoreApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}

