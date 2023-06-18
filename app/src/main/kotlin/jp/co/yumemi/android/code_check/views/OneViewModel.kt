/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.model.GithubServerResponse
import jp.co.yumemi.android.code_check.repository.GithubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for managing the data and business logic related to the OneFragment.
 * Uses dependency injection to retrieve an instance of [GithubRepository] for data retrieval.
 */
@HiltViewModel
class OneViewModel @Inject constructor(
    val githubRepository: GithubRepository
) : ViewModel() {

    private val _githubRepositoryList = MutableLiveData<List<GithubRepositoryData>>(null)
    val gitHubRepositoryList: LiveData<List<GithubRepositoryData>> get() = _githubRepositoryList

    /**
     * Perform a search for GitHub repositories based on the provided input text.
     * Updates the [_githubRepositoryList] with the search results.
     *
     * @param inputText The text used for searching GitHub repositories.
     */
    fun searchResults(inputText: String) {
// Call the getGitHutAccountFromDataSource function from the injected githubRepository
        viewModelScope.launch {
            val serverResponse: GithubServerResponse? =
                githubRepository.getGitHutAccountFromDataSource(inputText)
            // Update the _githubRepositoryList with the search results
            _githubRepositoryList.value = serverResponse?.items
        }
    }
}

