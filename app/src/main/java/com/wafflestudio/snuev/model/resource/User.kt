package com.wafflestudio.snuev.model.resource

import com.squareup.moshi.Json
import moe.banana.jsonapi2.HasOne
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "users")
class User: Resource() {
    val nickname: String = ""
    val username: String = ""
    @field:Json(name = "created_at") val createdAt: String = ""
    @field:Json(name = "updated_at") val updatedAt: String = ""
    val email: String = ""
    @field:Json(name = "is_confirmed") val isConfirmed: Boolean = false
    val department: HasOne<Department>? = null
}