package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.apiservices.GithubRepositoryApiService
import jp.co.yumemi.android.code_check.model.GithubServerResponse
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
     * @param query The search query for repositories.
     * @return The response containing GitHub server data, or null if an error occurred.
     */
    suspend fun getGitHubAccountFromDataSource(query: String): GithubServerResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext getResponseFromRemoteService(query)
        }
    }

    /**
     * Retrieves the response from the remote GitHub API service based on the provided query.
     *
     * @param query The search query for repositories.
     * @return The response containing GitHub server data, or null if an error occurred.
     */
    private suspend fun getResponseFromRemoteService(query: String): GithubServerResponse? {
        val response = githubRepositoryApiService.getRepositories(query)
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }
}