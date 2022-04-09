package com.ke.zhihu.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class CommentResponse(
    val id: String = "",
    val type: String = "",
    @Json(name = "resource_type")
    val resourceType: String = "",
    @Json(name = "member_id")
    val memberId: Int = 0,
    val url: String = "",
    val hot: Boolean = false,
    val top: Boolean = false,
    val content: String = "",
    val score: Int = 0,
    @Json(name = "created_time")
    val createdTime: Long,
    @Json(name = "is_delete")
    val isDelete: Boolean = false,
    val collapsed: Boolean = false,
    val reviewing: Boolean = false,
    @Json(name = "reply_comment_id")
    val replyCommentId: String = "",
    @Json(name = "reply_root_comment_id")
    val replyRootCommentId: String = "",
    val liked: Boolean = false,
    @Json(name = "like_count")
    val likeCount: Int = 0,
    val author: PeopleResponse = PeopleResponse(),
    @Json(name = "reply_to_author")
    val replyToAuthor: PeopleResponse? = null,
    @Json(name = "child_comment_count")
    val childCommentCount: Int = 0,
    @Json(name = "child_comments")
    val childComments: List<CommentResponse> = emptyList()
) : Serializable
