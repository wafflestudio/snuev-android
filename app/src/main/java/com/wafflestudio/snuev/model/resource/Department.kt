package com.wafflestudio.snuev.model.resource

import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "departments")
class Department : Resource() {
    val name: String = ""
}