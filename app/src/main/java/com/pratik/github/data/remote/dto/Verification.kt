package com.pratik.github.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Verification(
    @SerializedName("verified")
    var verified: Boolean,

    @SerializedName("reason")
    var reason: String,

    @SerializedName("signature")
    var signature: String,

    @SerializedName("payload")
    var payload: String
)