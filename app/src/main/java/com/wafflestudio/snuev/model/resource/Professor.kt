package com.wafflestudio.snuev.model.resource

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "professors")
class Professor : Resource() {
    val id: Int? = null
    val name: String = ""
    @field:Json(name = "created_at") val createdAt: String = ""
    @field:Json(name = "updated_at") val updatedAt: String = ""
}