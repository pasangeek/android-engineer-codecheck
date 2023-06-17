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
 * TwoFragment で使う
 */

@HiltViewModel
class OneViewModel @Inject constructor(
    val githubRepository: GithubRepository
) : ViewModel() {

    private val _githubRepositoryList = MutableLiveData<List<GithubRepositoryData>>(null)
    val gitHubRepositoryList: LiveData<List<GithubRepositoryData>> get() = _githubRepositoryList

    // 検索結果
    fun searchResults(inputText: String) {

        viewModelScope.launch {
            val serverResponse: GithubServerResponse? =
                githubRepository.getGitHutAccountFromDataSource(inputText)

            _githubRepositoryList.value = serverResponse?.items
        }
    }
}

