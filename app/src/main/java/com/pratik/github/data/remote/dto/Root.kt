package com.pratik.github.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Root(
    @SerializedName("sha")
    val sha: String,

    @SerializedName("node_id")
    val node_id: String,

    @SerializedName("commit")
    val commit: Commit ?=null,

    @SerializedName("url")
    val url: String,

    @SerializedName("html_url")
    val html_url: String,

    @SerializedName("comments_url")
    val comments_url: String,

    @SerializedName("author")
    val author: Author?=null,

    @SerializedName("committer")
    val committer: Committer?=null,

    @SerializedName("parents")
    val parents: List<Parent> ?= emptyList()
)