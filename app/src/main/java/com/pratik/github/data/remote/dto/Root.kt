package com.pratik.github.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Root(
    @SerializedName("sha")
    val sha: String,

    @SerializedName("node_id")
    val node_id: String,

    @SerializedName("commit")
    val commit: Commit,

    @SerializedName("url")
    val url: String,

    @SerializedName("html_url")
    val html_url: String,

    @SerializedName("comments_url")
    val comments_url: String,

    @SerializedName("author")
    val author: Author,

    @SerializedName("committer")
    val committer: Committer,

    @SerializedName("parents")
    val parents: List<Parent>
)