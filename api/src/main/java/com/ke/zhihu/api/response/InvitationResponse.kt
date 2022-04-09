package com.ke.zhihu.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class InvitationResponse(
    val type: String,
    val created: Long,
    @Json(name = "empty_info")
    val emptyInfo: Map<String, String> = emptyMap(),
    val id: String = "",
    @Json(name = "unique_id")
    val uniqueId: String = "",
    val head: InvitationHead = InvitationHead(),
    val content: InvitationContent = InvitationContent(),
    @Json(name = "target_source")
    val targetSource: InvitationTargetSource = InvitationTargetSource(),

    @Json(name = "is_read")
    val isRead: Boolean = true,
    @Json(name = "is_top")
    val isTop: Boolean = false
) {
    val date: String
        get() = simpleDateFormat.format(Date(created * 1000))
}

private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

@JsonClass(generateAdapter = true)
data class InvitationHead(
    @Json(name = "avatar_urls")
    val avatarUrls: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class InvitationContent(
    val title: String = "",
    @Json(name = "sub_title")
    val subTitle: String = "",
    val text: String = "",
    @Json(name = "sub_text")
    val subText: String = ""
)

@JsonClass(generateAdapter = true)
data class InvitationTargetSource(
    val text: String = "",
    @Json(name = "sub_text")
    val subText: String = ""
)
