package jp.co.yumemi.android.code_check.repository

import android.app.DownloadManager.Query
import jp.co.yumemi.android.code_check.apiservices.GithubRepositoryApiService
import jp.co.yumemi.android.code_check.model.GithubServerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepository @Inject constructor(private val githubRepositoryApiService: GithubRepositoryApiService){

    suspend fun getGitHutAccountFromDataSource(query: String): GithubServerResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext getResponseFromRemoteService(query)
        }
    }

    private suspend fun getResponseFromRemoteService(query: String): GithubServerResponse? {
        val response = githubRepositoryApiService.getRepositories(query)
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }
}