package com.wafflestudio.snuev.network

import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.model.resource.User
import io.reactivex.Observable
import moe.banana.jsonapi2.Document
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SnuevEndpoint {
    @POST("v1/user/sign_in")
    @FormUrlEncoded
    fun signIn(@Field("username") username: String,
               @Field("password") password: String): Observable<Document>

    @GET("v1/user")
    fun fetchUser(): Observable<User>

    @GET("v1/evaluations/latest")
    fun fetchLatestEvaluations(): Observable<List<Evaluation>>

    @GET("v1/lectures/most_evaluated")
    fun fetchMostEvaluatedLectures(): Observable<List<Lecture>>

    @GET("v1/lectures/top_rated")
    fun fetchTopRatedLectures(): Observable<List<Lecture>>

    @GET("v1/evaluations/most_liked")
    fun fetchMostLikedEvaluations(): Observable<List<Evaluation>>
}