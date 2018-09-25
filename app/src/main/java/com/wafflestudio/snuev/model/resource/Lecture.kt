package com.wafflestudio.snuev.model.resource

import com.squareup.moshi.Json
import moe.banana.jsonapi2.HasOne
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "lectures")
class Lecture : Resource() {
    @field:Json(name = "name")
    val name: String = ""
    @field:Json(name = "score")
    val score: Float = 0f
    @field:Json(name = "easiness")
    val easiness: Float = 0f
    @field:Json(name = "grading")
    val grading: Float = 0f
    @field:Json(name = "view_count")
    val viewCount: Int = 0
    @field:Json(name = "evaluation_count")
    val evaluationCount: Int = 0
    @field:Json(name = "bookmarked")
    val bookmarked: Boolean = false
    @field:Json(name = "evaluated")
    val evaluated: Boolean = false
    @field:Json(name = "created_at")
    val createdAt: String = ""
    @field:Json(name = "updated_at")
    val updatedAt: String = ""

    private val course: HasOne<Course>? = null
    fun getCourse(): Course? = course?.get(document)
    private val professor: HasOne<Professor>? = null
    fun getProfessor() = professor?.get(document)
}