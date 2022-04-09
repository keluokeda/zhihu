package com.ke.zhihu.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FollowResponse(
    @Json(name = "is_following")
    val isFollowing: Boolean = false
)
