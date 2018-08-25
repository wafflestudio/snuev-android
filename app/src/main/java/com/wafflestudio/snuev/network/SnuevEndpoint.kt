package com.wafflestudio.snuev.network

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
}