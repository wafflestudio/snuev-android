package com.wafflestudio.snuev.model.resource

import com.squareup.moshi.Json
import moe.banana.jsonapi2.HasOne
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "courses")
class Course : Resource() {
    @field:Json(name = "name")
    val name: String = ""
    @field:Json(name = "target_grade")
    val targetGrade: String = ""
    @field:Json(name = "category")
    val category: Int? = null
    @field:Json(name = "lecture_unit")
    val lectureUnit: Int = 0
    @field:Json(name = "lab_unit")
    val labUnit: Int = 0
    @field:Json(name = "total_unit")
    val totalUnit: Int = 0
    @field:Json(name = "created_at")
    val createdAt: String = ""
    @field:Json(name = "updated_at")
    val updatedAt: String = ""

    private val department: HasOne<Department>? = null
    fun getDepartment() = department?.get(document)
}