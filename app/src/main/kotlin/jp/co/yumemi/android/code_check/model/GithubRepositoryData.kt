package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepositoryData (

    val name: String?,
    val owner: Owner?,
    val language: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Long?,
    @SerializedName("watchers_count")
    val watchersCount: Long?,
    val forksCount: Long?,
    val openIssuesCount: Long?,

    ):Parcelable