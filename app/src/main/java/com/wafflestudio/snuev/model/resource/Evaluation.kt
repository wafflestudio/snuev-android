package com.wafflestudio.snuev.model.resource

import com.squareup.moshi.Json
import moe.banana.jsonapi2.HasOne
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "evaluations")
class Evaluation : Resource() {
    @field:Json(name = "score")
    var score: Float = 0f
    @field:Json(name = "easiness")
    var easiness: Float = 0f
    @field:Json(name = "grading")
    var grading: Float = 0f
    @field:Json(name = "comment")
    var comment: String = ""
    @field:Json(name = "upvotes_count")
    val upVotesCount: Int = 0
    @field:Json(name = "downvotes_count")
    val downVotesCount: Int = 0
    @field:Json(name = "downVoted")
    val downVoted: Boolean = false
    @field:Json(name = "upVoted")
    val upVoted: Boolean = false
    @field:Json(name = "can_destroy")
    val canDestroy: Boolean = false
    @field:Json(name = "can_update")
    val canUpdate: Boolean = false
    @field:Json(name = "created_at")
    val createdAt: String = ""
    @field:Json(name = "updated_at")
    val updatedAt: String = ""

    private val lecture: HasOne<Lecture>? = null
    fun getLecture() = lecture?.get(document)
    private val semester: HasOne<Semester>? = null
    fun getSemester() = semester?.get(document)
}