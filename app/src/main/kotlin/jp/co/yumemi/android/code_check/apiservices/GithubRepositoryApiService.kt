package jp.co.yumemi.android.code_check.apiservices

import jp.co.yumemi.android.code_check.constant.Constants
import jp.co.yumemi.android.code_check.model.GithubServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubRepositoryApiService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET(Constants.REPOSITORIES_ENDPOINT)
    suspend fun getRepositories(@Query("q") q: String): Response<GithubServerResponse>
}