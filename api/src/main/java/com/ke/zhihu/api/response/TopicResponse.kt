package com.ke.zhihu.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopicResponse(
    val id: String,
    val type: String,
    val url: String,
    val name: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "topic_type")
    val topicType: String
)
