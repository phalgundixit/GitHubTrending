package com.phalgundixit.githubtrending.data.model

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContributorsResponse (
    @SerializedName("login") var login: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
    @SerializedName("repos_url") var reposUrl: String? = null,
    @SerializedName("url") var url: String? = null
        ):Parcelable