package jp.co.yumemi.android.code_check.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor() : ViewModel() {

    // LiveData to hold the details of a GitHub repository
    private val _githubRepositoryDetail = MutableLiveData<GithubRepositoryData>(null)

    // LiveData to observe the GitHub repository details
    val gitHubRepositoryDetails: LiveData<GithubRepositoryData>
        get() = _githubRepositoryDetail

    // Initialize the ViewModel and log the initialization
    init {
        logMessage("RepositoryDetail ViewModel initialized")
    }

    /**
     * Set the details of a GitHub repository and update LiveData.
     *
     * @param githubRepositoryData The GitHub repository data to set.
     */
    fun setRepositoryDetails(githubRepositoryData: GithubRepositoryData) {
        _githubRepositoryDetail.value = githubRepositoryData
        logMessage("Repository details set: $githubRepositoryData")
    }

    override fun onCleared() {
        super.onCleared()
        // Log when the ViewModel is cleared
        logMessage("ViewModel cleared")
    }

    // Helper function for logging messages with a specified tag
    private fun logMessage(message: String) {
        Log.d("RepositoryDetailViewModel", message)
    }
}