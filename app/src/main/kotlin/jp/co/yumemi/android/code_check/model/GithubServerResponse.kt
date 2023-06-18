package jp.co.yumemi.android.code_check.model

/**
 * Represents the response received from the GitHub server.
 *
 * @property total_count The total count of repositories matching the search criteria.
 * @property incomplete_results Indicates whether the server's response is incomplete or not.
 * @property items The list of individual repository data returned by the server.
 */
data class GithubServerResponse(

    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<GithubRepositoryData>
)