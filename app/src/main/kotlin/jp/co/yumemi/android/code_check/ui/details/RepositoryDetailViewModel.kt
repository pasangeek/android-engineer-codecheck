package jp.co.yumemi.android.code_check.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.repository.GithubRepository
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    val githubRepository: GithubRepository
): ViewModel() {

    private val _githubRepositoryDetail = MutableLiveData<GithubRepositoryData>(null)


    val gitHubRepositoryDetails: LiveData<GithubRepositoryData>
        get() = _githubRepositoryDetail


    init {
        Log.d("RepositoryDetailViewModel", "RepositoryDetail ViewModel initialized")
    }

    fun setRepositoryDetails(githubRepositoryData: GithubRepositoryData) {
        _githubRepositoryDetail.value = githubRepositoryData
        Log.d("RepositoryDetailViewModel", "Repository details set: $githubRepositoryData")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("RepositoryDetailViewModel", "ViewModel cleared")
    }
}