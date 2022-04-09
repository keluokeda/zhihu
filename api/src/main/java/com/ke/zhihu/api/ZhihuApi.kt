package com.ke.zhihu.api

import com.ke.zhihu.api.response.*
import retrofit2.http.*

interface ZhihuApi {


    /**
     * 检查token是否有效
     */
    @GET("people/self?action=ignore_token")
    suspend fun checkToken(
//        @Header("Authorization") authorization: String
    ): PeopleResponse

    /**
     * 获取某个用户的粉丝
     */
    @GET("people/{peopleId}/followers")
    suspend fun getFollowers(
        @Path("peopleId") peopleId: String,
        @Query("offset") offset: Int = 0
    ): BaseJsonListResponse<PeopleResponse>


    /**
     * 获取首页文章
     */
    @GET
    suspend fun getHomeArticleList(
        @Url url: String
    ): BaseJsonListResponse<HomeArticleResponse>

    /**
     * 邀请回答 推荐
     */
    @GET("creators/personalized-questions/recommended?page_source=&page_source=invite")
    suspend fun getRecommendInvitationList(
        @Query("offset") offset: Int
    ): BaseJsonListResponse<InvitationAnswerResponse>

    /**
     * 邀请回答 邀请
     */
    @GET
    suspend fun getInvitationList(
        @Url url: String
    ): BaseJsonListResponse<InvitationResponse>

    /**
     * 新增关注
     */
    @GET
    suspend fun getNewFollowList(
        @Url url: String
    ): BaseJsonListResponse<InvitationResponse>

    /**
     * 赞同与喜欢
     */
    @GET
    suspend fun getLikeList(
        @Url url: String
    ): BaseJsonListResponse<InvitationResponse>

    /**
     * 消息列表
     */
    @GET
    suspend fun getMessageList(
        @Url url: String
    ): BaseJsonListResponse<InvitationResponse>

    /**
     * 全部已读
     */
    @POST("notifications/v3/message/readall")
    suspend fun readAllMessages()

    /**
     * 获取问题详情
     */
    @GET("questions/{questionId}?include=read_count")
    suspend fun getQuestionDetail(
        @Path("questionId") questionId: String
    ): QuestionResponse

    /**
     * 获取某个问题的回答
     */
    @GET("v4/questions/{questionId}/answers?order_by=default&show_detail=1")
    suspend fun getAnswerList(
        @Path("questionId") questionId: String,
        @Query("offset") offset: Int = 0
    ): BaseJsonListResponse<AnswerItemResponse>

    /**
     * 获取关注某个问题的人
     */
    @GET("questions/{questionId}/followers")
    suspend fun getQuestionFollowers(
        @Path("questionId") questionId: String,
        @Query("offset") offset: Int = 0
    ): BaseJsonListResponse<PeopleResponse>

    /**
     * 关注某个人
     */
    @POST("people/{peopleId}/followers")
    suspend fun followPeople(
        @Path("peopleId") peopleId: String
    ): FollowResponse

    /**
     * 取消关注某个人
     */
    @DELETE("people/{peopleId}/followers/{userId}")
    suspend fun cancelFollowPeople(
        @Path("peopleId") peopleId: String,
        @Path("userId") userId: String,
    ): FollowResponse

    /**
     * 获取某个回答或问题的评论
     */
    @GET
    suspend fun getComments(
        @Url url: String
    ): BaseJsonListResponse<CommentResponse>

    /**
     * 获取一个评论的回复
     */
    @GET
    suspend fun getChildComments(
        @Url url: String
    ): BaseJsonListResponse<CommentResponse>

    /**
     * 一键邀请
     */
    @FormUrlEncoded
    @POST("questions/{questionId}/invitations/batch")
    suspend fun oneKeyInvitations(
        @Path("questionId") questionId: String,
        @Field("src") src: String = "normal",
        @Field("member_hashes") hashes: String
    ): ActionResponse

    @GET("v4/answers/{answerId}/question")
    suspend fun getQuestionByAnswerId(
        @Path("answerId") answerId: String,
    ): QuestionResponse
}