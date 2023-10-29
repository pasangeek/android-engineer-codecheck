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
    // LiveData to hold the list of GitHub repositories
    private val _githubRepositoryList = MutableLiveData<List<GithubRepositoryData>>(null)

    // Call the getGitHubAccountFromDataSource function from the injected githubRepository
    val gitHubRepositoryList: LiveData<List<GithubRepositoryData>> get() = _githubRepositoryList

    /**
     * Perform a search for GitHub repositories based on the provided input text.
     * Updates the [_githubRepositoryList] with the search results.
     *
     * @param inputText The text used for searching GitHub repositories.
     */
    fun searchResults(inputText: String) {
        logMessage( "Searching GitHub repositories with input: $inputText")
        // Call the getGitHubAccountFromDataSource function from the injected githubRepository
        viewModelScope.launch {
            try {
                val serverResponse: GithubServerResponse? =
                    githubRepository.getGitHubAccountFromDataSource(inputText)
                if (serverResponse != null) {
                    logMessage( "Search results received: ${serverResponse.items?.size} items")
                    // Update the _githubRepositoryList with the search results
                    _githubRepositoryList.value = serverResponse.items
                } else {
                    logMessage( "Search results are null or empty")
                }
            } catch (e: Exception) {
                logMessage( "Error during search: ${e.message}")
            }
        }
}

    /**
     * Helper function for logging messages with a specified tag.
     *
     * @param message The message to log.
     */
    private fun logMessage(message: String) {
        Log.d("SearchViewModel", message)
    }

}

