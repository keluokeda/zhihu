package com.ke.zhihu.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class PeopleResponse(
    val id: String = "",

    @Json(name = "avatar_url")
    val avatarUrl: String = "",

    val name: String = "",

    val headline: String = "",

    /**
     * 是否关注了别人
     */
    @Json(name = "is_following")
    var isFollowing: Boolean = false,

    /**
     * 是否被别人关注
     */
    @Json(name = "is_followed")
    val isFollowed: Boolean = false
) : Serializable
