package com.ke.zhihu.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionResponse(
    val type: String,
    val id: Long,
    val title: String,
    @Json(name = "question_type")
    val questionType: String,
    val created: Long,
    @Json(name = "updated_time")
    val updatedTime: Long,
    val author: PeopleResponse = PeopleResponse(),

    @Json(name = "answer_count")
    val answerCount: Int = 0,
    @Json(name = "follower_count")
    val followerCount: Int = 0,

    @Json(name = "comment_count")
    val commentCount: Int = 0,
    val message: String = "",
    val except: String = "",
    val detail: String = "",

    val topics: List<TopicResponse> = emptyList(),

    )