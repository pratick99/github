package com.pratik.github.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Parent(
    @SerializedName("sha")
    val sha: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("html_url")
    val html_url: String
)
