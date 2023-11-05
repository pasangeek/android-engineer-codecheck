package jp.co.yumemi.android.code_check.repository

import android.util.Log
import jp.co.yumemi.android.code_check.sources.GithubRepositoryApiService
import jp.co.yumemi.android.code_check.data.model.GithubServerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Represents a repository for accessing GitHub-related data.
 *
 * @param githubRepositoryApiService The API service used for making GitHub API requests.
 */
class GithubRepository @Inject constructor(private val githubRepositoryApiService: GithubRepositoryApiService) {

    /**
     * Retrieves GitHub account data from the data source based on the provided query.
     *
     * @param searchQuery The search query for repositories.
     * @return The response containing GitHub server data, or null if an error occurred.
     */
    suspend fun getGitHubAccountFromDataSource(searchQuery: String): GithubServerResponse? {
        try {
            return withContext(Dispatchers.IO) {
                return@withContext getResponseFromRemoteService(searchQuery)
            }
        } catch (e: Exception) {
            logMessage(  "NetworkError during data retrieval: ${e.message}")
            return null
        }
    }

    /**
     * Retrieves the response from the remote GitHub API service based on the provided query.
     *
     * @param responseQuery The search query for repositories.
     * @return The response containing GitHub server data, or null if an error occurred.
     */
    private suspend fun getResponseFromRemoteService(responseQuery: String): GithubServerResponse? {
        return try {
            val response = githubRepositoryApiService.getRepositories(responseQuery)
            if (response.isSuccessful) {
                response.body()
            } else {
                logMessage(  "GitHub API request failed with code: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            logMessage(  "NetworkError during API request: ${e.message}")
            null
        }
    }

    private fun logMessage(message: String) {
        Log.d("GithubRepository", message)
    }

}