package com.ke.zhihu.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeArticleResponse(
    val id: String,
    val type: String,
    val offset: Int,
    @Json(name = "common_card")
    val commonCard: CommonCard,

    val extra: Map<String, String> = emptyMap()
)

@JsonClass(generateAdapter = true)
data class CommonCard(
    val style: String,
    @Json(name = "feed_content")
    val feedContent: CommonCardFeedContent,
    @Json(name = "footline")
    val footLine: CommonCardFeedContentSourceLine? = null
)

@JsonClass(generateAdapter = true)
data class CommonCardFeedContent(
    val image: CommonCardFeedContentImage? = null,
    val video: Map<String, Any>? = null,
    val title: CommonCardFeedContentTitle,
    val content: CommonCardFeedContentTitle?,
    @Json(name = "source_line")
    val sourceLine: CommonCardFeedContentSourceLine
)

@JsonClass(generateAdapter = true)
data class CommonCardFeedContentTitle(
    @Json(name = "max_line")
    val maxLine: Int,
    @Json(name = "panel_text")
    val panelText: String
)

@JsonClass(generateAdapter = true)
data class CommonCardFeedContentImage(
    @Json(name = "image_url")
    val imageUrl: String
)

@JsonClass(generateAdapter = true)
data class CommonCardFeedContentSourceLine(
    val elements: List<Payload> = emptyList(),
) {
    val footerString: String?
        get() {
            return elements.firstOrNull()?.run {
                text?.panelText ?: tag?.text
            }
        }
}

@JsonClass(generateAdapter = true)
data class Payload(
    val avatar: Avatar? = null,
    val text: Text? = null,
    val tag: Tag? = null
)

//@JsonClass(generateAdapter = true)
//data class CommonCardFootLine(
//    val elements: List<Map<String, *>>,
//    @Json(name = "second_elements")
//)

@JsonClass(generateAdapter = true)
data class Text(
    @Json(name = "panel_text")
    val panelText: String = ""
)

@JsonClass(generateAdapter = true)
data class Tag(
    val text: String = ""
)

@JsonClass(generateAdapter = true)
data class Avatar(
    val image: Image
)

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "image_url")
    val url: String
)


