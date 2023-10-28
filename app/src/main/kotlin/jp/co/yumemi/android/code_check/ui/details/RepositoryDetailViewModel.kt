package jp.co.yumemi.android.code_check.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData

class RepositoryDetailViewModel (
): ViewModel() {

    private val _githubRepositoryDetail = MutableLiveData<GithubRepositoryData>(null)


    val gitHubRepositoryDetails: LiveData<GithubRepositoryData>
        get() = _githubRepositoryDetail



    fun setRepositoryDetails(githubRepositoryData: GithubRepositoryData) {
        _githubRepositoryDetail.value = githubRepositoryData
    }

}