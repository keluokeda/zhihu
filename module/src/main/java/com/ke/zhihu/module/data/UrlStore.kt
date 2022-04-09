package com.ke.zhihu.module.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrlStore @Inject constructor(private val keyValueStore: KeyValueStore) {


    var homePreviousUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_HOME_PREVIOUS, value)
        }
        get() = keyValueStore.getString(KEY_HOME_PREVIOUS, null)

    var homeNextUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_HOME_NEXT, value)
        }
        get() = keyValueStore.getString(KEY_HOME_NEXT, null)

    var invitationPreviousUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_INVITATION_PREVIOUS, value)
        }
        get() = keyValueStore.getString(KEY_INVITATION_PREVIOUS, null)

    var invitationNextUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_INVITATION_NEXT, value)
        }
        get() = keyValueStore.getString(KEY_INVITATION_NEXT, null)


    var likeNextUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_LIKE_NEXT, value)
        }
        get() = keyValueStore.getString(KEY_LIKE_NEXT, null)

    var newFollowNextUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_NEW_FOLLOW_NEXT, value)
        }
        get() = keyValueStore.getString(KEY_NEW_FOLLOW_NEXT, null)

    var messageListNextUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_MESSAGE_NEXT, value)
        }
        get() = keyValueStore.getString(KEY_MESSAGE_NEXT, null)

    var commentsNextUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_COMMENTS_NEXT, value)
        }
        get() = keyValueStore.getString(KEY_COMMENTS_NEXT, null)

    var childCommentsNextUrl: String?
        set(value) {
            keyValueStore.saveString(KEY_CHILD_COMMENT_NEXT, value)
        }
        get() = keyValueStore.getString(KEY_CHILD_COMMENT_NEXT, null)

    /**
     * 退出的时候清空
     */
    fun clearHomeUrl() {
        homePreviousUrl = null
        homeNextUrl = null
    }

    fun clearInvitationUrl() {
        invitationNextUrl = null
        invitationPreviousUrl = null
    }

    companion object {
        const val KEY_HOME_PREVIOUS = "KEY_HOME_PREVIOUS"
        const val KEY_HOME_NEXT = "KEY_HOME_NEXT"

        const val KEY_INVITATION_PREVIOUS = "KEY_INVITATION_PREVIOUS"
        const val KEY_INVITATION_NEXT = "KEY_INVITATION_NEXT"

        const val KEY_LIKE_NEXT = "KEY_LIKE_NEXT"

        const val KEY_NEW_FOLLOW_NEXT = "KEY_NEW_FOLLOW_NEXT"

        const val KEY_MESSAGE_NEXT = "KEY_MESSAGE_NEXT"

        const val KEY_COMMENTS_NEXT = "KEY_COMMENTS_NEXT"

        const val KEY_CHILD_COMMENT_NEXT = "KEY_CHILD_COMMENT_NEXT"

    }
}