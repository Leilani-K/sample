package com.example.gettingstartedwithjetpackcompose.ota

import com.google.gson.annotations.SerializedName

data class VersionInfo(
    @SerializedName("latest_version_code") val latestVersionCode: Int,
    @SerializedName("latest_version_name") val latestVersionName: String,
    @SerializedName("apk_url") val apkUrl: String,
    @SerializedName("release_notes") val releaseNotes: String
)