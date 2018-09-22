package com.wafflestudio.snuev.model.request

import com.squareup.moshi.Json

data class SignUpRequest(
        @Json(name = "username") val username: String,
        @Json(name = "department_id") val departmentId: String,
        @Json(name = "nickname") val nickname: String,
        @Json(name = "password") val password: String
)