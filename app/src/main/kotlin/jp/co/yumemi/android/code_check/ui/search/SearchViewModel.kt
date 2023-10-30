/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.util.Log
import android.widget.Toast
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
    val githubRepository: GithubRepository,
) : AndroidViewModel(application) {
    private val _githubRepositoryList = MutableLiveData<List<GithubRepositoryData>>(null)
    val gitHubRepositoryList: LiveData<List<GithubRepositoryData>> get() = _githubRepositoryList
    private val _errorLiveData = MutableLiveData<ErrorState>()
    val errorLiveData: LiveData<ErrorState> get() = _errorLiveData
    fun searchResults(inputText: String) {
        if (!isInternetConnectionAvailable()) {
            logMessage("No internet connection available.")
            _errorLiveData.value = ErrorState.Error("No internet connection available")
         // showErrorDialog("No internet connection available.")

            return
        }

        logMessage("Searching GitHub repositories with input: $inputText")

        viewModelScope.launch {
            try {
                val serverResponse: GithubServerResponse? =
                    githubRepository.getGitHubAccountFromDataSource(inputText)

                if (serverResponse != null) {
                    logMessage("Search results received: ${serverResponse.items?.size} items")
                    _githubRepositoryList.value = serverResponse.items
                } else {
                    showSearchResultsEmptyMessage()
                    logMessage("Search results are null or empty")
                }
            } catch (e: Exception) {
                logMessage("Error during search: ${e.message}")
            }
        }
    }
    private fun showSearchResultsEmptyMessage() {
        Toast.makeText(getApplication(), "Search results are null or empty", Toast.LENGTH_SHORT).show()


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

