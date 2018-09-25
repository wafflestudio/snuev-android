package com.wafflestudio.snuev.model.resource

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "departments")
class Department : Resource() {
    @field:Json(name = "name")
    val name: String = ""
}