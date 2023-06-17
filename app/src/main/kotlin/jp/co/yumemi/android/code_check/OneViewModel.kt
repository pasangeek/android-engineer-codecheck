/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.model.GithubServerResponse
import jp.co.yumemi.android.code_check.repository.GithubRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.util.*
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

