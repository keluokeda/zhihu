package com.ke.zhihu.api.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseJsonListResponse<out T>(
    val data: List<T> = emptyList(),
    val paging: Paging
)

@JsonClass(generateAdapter = true)
data class Paging(
    val previous: String?,
    val next: String?,
)
