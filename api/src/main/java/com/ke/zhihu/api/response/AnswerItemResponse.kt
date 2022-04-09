package com.ke.zhihu.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class AnswerItemResponse(
    @Json(name = "answer_type")
    val answerType: String,
    val author: PeopleResponse = PeopleResponse(),
    /**
     * 评论
     */
    @Json(name = "comment_count")
    val commentCount: Int = 0,
    @Json(name = "created_time")
    val createdTime: Long,
    val excerpt: String,

    val id: Long,
    /**
     * 赞同
     */
    @Json(name = "voteup_count")
    val voteUpCount: Int = 0,

    /**
     * 感谢
     */
    @Json(name = "thanks_count")
    val thanksCount: Int = 0,

    val url: String,

    @Json(name = "thumbnail_info")
    val thumbnailInfo: AnswerItemThumbnailInfo = AnswerItemThumbnailInfo()
) {
    val footerString: String
        get() {
            val stringBuilder = StringBuilder()

            if (voteUpCount != 0) {
                stringBuilder.append(voteUpCount)
                stringBuilder.append("赞同")
                stringBuilder.append("·")
            }

            if (thanksCount != 0) {
                stringBuilder.append(thanksCount)
                stringBuilder.append("喜欢")
                stringBuilder.append("·")
            }

            if (commentCount != 0) {
                stringBuilder.append(commentCount)
                stringBuilder.append("评论")
                stringBuilder.append("·")
            }

            stringBuilder.append(simpleDateFormat.format(Date(createdTime * 1000)))

            return stringBuilder.toString()
        }
}

@JsonClass(generateAdapter = true)
data class AnswerItemThumbnailInfo(
    val count: Int = 0,
    val thumbnails: List<AnswerItemThumbnail> = emptyList()
)

@JsonClass(generateAdapter = true)
data class AnswerItemThumbnail(
    val url: String
)

private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
