/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.common.ErrorState

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
    private val githubRepository: GithubRepository,
) : AndroidViewModel(application) {
    private val _githubRepositoryList = MutableLiveData<List<GithubRepositoryData>>(null)
    val gitHubRepositoryList: LiveData<List<GithubRepositoryData>> get() = _githubRepositoryList
    var errorState = MutableLiveData<ErrorState>()
    val errorLiveData: LiveData<ErrorState> get() = errorState

    // Add a LiveData for the loading state
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun searchResults(inputText: String) {
        if (!isInternetConnectionAvailable()) {
            logMessage("No internet connection available.")
            errorState.value = ErrorState.NetworkError("No internet connection available")
         // showErrorDialog("No internet connection available.")

            return
        }

        logMessage("Searching GitHub repositories with input: $inputText")
        // Set the loading state to true when searching begins
        _loading.value = true
        viewModelScope.launch {
            try {
                val serverResponse: GithubServerResponse? =
                    githubRepository.getGitHubAccountFromDataSource(inputText)

                if (serverResponse != null) {
                    logMessage("Search results received: ${serverResponse.items.size} items")
                    _githubRepositoryList.value = serverResponse.items



                } else {
                    showSearchResultsEmptyMessage()
                    logMessage("Search results are null or empty")
                }
            } catch (e: Exception) {
                logMessage("NetworkError during search: ${e.message}")
            }
            finally {
                // Set the loading state to false when the search is complete
                _loading.value = false
            }
        }
    }
    private fun showSearchResultsEmptyMessage() {

      errorState.value = ErrorState.NetworkError("Search results are null or empty")

    }

    private fun isInternetConnectionAvailable(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(ConnectivityManager::class.java)
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities?.run {
            hasTransport(TRANSPORT_WIFI) ||
                    hasTransport(TRANSPORT_CELLULAR)

        } ?: false
    }

    private fun logMessage(message: String) {
        Log.d("SearchViewModel", message)
    }
}

