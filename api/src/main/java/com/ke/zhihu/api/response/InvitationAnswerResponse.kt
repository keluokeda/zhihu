package com.ke.zhihu.api.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InvitationAnswerResponse(
    val type: String,
    val question: QuestionResponse,
    val reason: String
)
