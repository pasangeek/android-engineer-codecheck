package jp.co.yumemi.android.code_check.sources

import jp.co.yumemi.android.code_check.common.Constants
import jp.co.yumemi.android.code_check.data.model.GithubServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubRepositoryApiService {
    /**
     * Retrieves a list of repositories from the GitHub API based on the provided query.
     *
     * @param q The search query for repositories.
     * @return A Response object containing the server response with the list of repositories.
     */
    @Headers("Accept: application/vnd.github.v3+json")
    @GET(Constants.REPOSITORIES_ENDPOINT)
    suspend fun getRepositories(@Query("q") q: String): Response<GithubServerResponse>
}