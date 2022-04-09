package com.ke.zhihu.module.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.zhihu.api.response.CommentResponse
import com.ke.zhihu.module.databinding.ModuleItemCommentBinding
import com.ke.zhihu.module.toDateString
import com.ke.zhihu.module.toHtml

class CommentAdapter constructor(
    private val activity: AppCompatActivity,
    private val afterBindData: (
        CommentResponse,
        ModuleItemCommentBinding
    ) -> Unit
) :
    BaseViewBindingAdapter<CommentResponse, ModuleItemCommentBinding>() {
    override fun bindItem(
        item: CommentResponse,
        viewBinding: ModuleItemCommentBinding,
        viewType: Int,
        position: Int
    ) {
        bindComment(item, viewBinding, activity)
        afterBindData(item, viewBinding)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ModuleItemCommentBinding {
        return ModuleItemCommentBinding.inflate(inflater, parent, false)
    }
}


fun bindComment(
    item: CommentResponse,
    viewBinding: ModuleItemCommentBinding,
    activity: AppCompatActivity
) {
    Glide.with(activity)
        .load(item.author.avatarUrl)
        .into(viewBinding.avatar)
    viewBinding.name.text = item.author.name
    viewBinding.content.text = item.content.toHtml()
    viewBinding.like.text = if (item.likeCount > 0) item.likeCount.toString() else ""
    viewBinding.date.text = item.createdTime.toDateString()

    viewBinding.target.isVisible = item.replyToAuthor != null
    viewBinding.target.text = item.replyToAuthor?.name
}