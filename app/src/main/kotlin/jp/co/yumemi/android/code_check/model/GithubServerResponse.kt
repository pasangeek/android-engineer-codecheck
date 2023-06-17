package jp.co.yumemi.android.code_check.model

data class GithubServerResponse (

    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<GithubRepositoryData>
        )