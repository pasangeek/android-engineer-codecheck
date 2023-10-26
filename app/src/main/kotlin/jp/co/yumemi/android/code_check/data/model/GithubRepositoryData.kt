package jp.co.yumemi.android.code_check.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
/**
 * Represents a GitHub repository data.
 *
 * @param name The name of the repository.
 * @param owner The owner of the repository.
 * @param language The programming language used in the repository.
 * @param stargazersCount The number of stargazers (users who starred) the repository has.
 * @param watchersCount The number of users watching the repository.
 * @param forksCount The number of forks (copies) the repository has.
 * @param openIssuesCount The number of open issues in the repository.
 */
@Parcelize
data class GithubRepositoryData(

    val name: String?,
    val owner: Owner?,
    val language: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Long?,
    @SerializedName("watchers_count")
    val watchersCount: Long?,
    val forksCount: Long?,
    val openIssuesCount: Long?,

    ) : Parcelable