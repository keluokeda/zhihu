package com.ke.zhihu.api.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActionResponse(
    val success: Boolean
)
