package com.pratik.github.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Commit(
    @SerializedName("author")
    val author: Author,

    @SerializedName("committer")
    val committer: Committer,

    @SerializedName("message")
    val message: String,

    @SerializedName("tree")
    val tree: Tree,

    @SerializedName("url")
    val url: String,

    @SerializedName("comment_count")
    val comment_count: Int,

    @SerializedName("verification")
    val verification: Verification
)