package jp.co.yumemi.android.code_check.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Represents an owner of a GitHub repository.
 *
 * @param avatarUrl The URL of the owner's avatar image.
 */
@Parcelize
data class Owner(
    @SerializedName("avatar_url") val avatarUrl: String,
  //  @SerializedName("followers")
   // val followers_url:String,
   // @SerializedName("following")
   // val following_url :String


    ) : Parcelable
