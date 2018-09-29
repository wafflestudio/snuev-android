package com.wafflestudio.snuev.network

import com.wafflestudio.snuev.model.request.SignUpRequest
import com.wafflestudio.snuev.model.resource.*
import io.reactivex.Observable
import moe.banana.jsonapi2.Document
import retrofit2.http.*

interface SnuevEndpoint {
    @POST("v1/user/sign_in")
    @FormUrlEncoded
    fun signIn(@Field("username") username: String,
               @Field("password") password: String): Observable<Document>

    @GET("v1/user")
    fun fetchUser(): Observable<User>

    @POST("v1/user")
    fun signUp(@Body body: SignUpRequest): Observable<Document>

    @GET("v1/evaluations/latest")
    fun fetchLatestEvaluations(): Observable<List<Evaluation>>

    @GET("v1/lectures/most_evaluated")
    fun fetchMostEvaluatedLectures(): Observable<List<Lecture>>

    @GET("v1/lectures/top_rated")
    fun fetchTopRatedLectures(): Observable<List<Lecture>>

    @GET("v1/evaluations/most_liked")
    fun fetchMostLikedEvaluations(): Observable<List<Evaluation>>

    @GET("v1/departments")
    fun fetchDepartments(): Observable<List<Department>>

    @GET("v1/courses/search")
    fun searchCourses(@Query("q") query: String): Observable<List<Course>>

    @GET("v1/lectures/search")
    fun searchLectures(@Query("q") query: String): Observable<List<Lecture>>

    @GET("v1/lectures/{lecture_id}")
    fun fetchLecture(@Path("lecture_id") lectureId: String): Observable<Lecture>

    @GET("v1/lectures/{lecture_id}/evaluations")
    fun fetchLectureEvaluations(@Path("lecture_id") lectureId: String, @Query("page") page: Int): Observable<List<Evaluation>>

    @POST("v1/lectures/{lecture_id}/evaluations")
    fun createEvaluation(@Path("lecture_id") lectureId: String, @Body body: Evaluation): Observable<Evaluation>

    @POST("/v1/lectures/{lecture_id}/evaluations/{evaluation_id}/vote")
    fun vote(
            @Path("lecture_id") lectureId: String,
            @Path("evaluation_id") evaluationId: String,
            @Query("vote[direction]") isUpVote: Boolean
    ): Observable<Document>

    @POST("v1/lectures/{lecture_id}/bookmark")
    fun bookmark(@Path("lecture_id") lectureId: String): Observable<Document>

    @DELETE("v1/lectures/{lecture_id}/bookmark")
    fun unBookmark(@Path("lecture_id") lectureId: String): Observable<Document>

    @GET("/v1/evaluations/mine")
    fun fetchMyEvaluations(): Observable<List<Evaluation>>

    @GET("/v1/lectures/bookmarked")
    fun fetchBookmarked(): Observable<List<Lecture>>
}