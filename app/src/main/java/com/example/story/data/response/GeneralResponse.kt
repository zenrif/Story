package com.example.story.data.response

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)