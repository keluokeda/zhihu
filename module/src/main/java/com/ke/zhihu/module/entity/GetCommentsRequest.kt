package com.ke.zhihu.module.entity

import android.os.Parcelable
import androidx.annotation.IdRes
import com.ke.zhihu.module.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCommentsRequest(
    val commentType: CommentType,
    val id: String,
    var commentSortType: CommentSortType = CommentSortType.Default
) : Parcelable


enum class CommentType {
    Questions,
    Answers
}

enum class CommentSortType(@IdRes val menuId: Int) {
    /**
     * 默认
     */
    Default(R.id.sort_default),

    /**
     * 最新的
     */
    Latest(R.id.sort_latest)
}

