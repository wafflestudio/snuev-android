package com.wafflestudio.snuev.model.resource

import android.content.Context
import com.squareup.moshi.Json
import com.wafflestudio.snuev.R
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "semesters")
class Semester : Resource() {
    @field:Json(name = "year")
    val year: Int = 0
    @field:Json(name = "season")
    private val season: String = ""

    fun getSeason(): Season {
        return when (season) {
            Season.SPRING.value -> Season.SPRING
            Season.SUMMER.value -> Season.SUMMER
            Season.FALL.value -> Season.FALL
            Season.WINTER.value -> Season.WINTER
            else -> Season.BLANK
        }
    }
}

enum class Season(val value: String) {
    SPRING("spring"),
    SUMMER("summer"),
    FALL("fall"),
    WINTER("winter"),
    BLANK("")
}

fun Semester.toString(context: Context): String {
    return when (getSeason()) {
        Season.SPRING -> context.getString(R.string.semester_spring, year)
        Season.SUMMER -> context.getString(R.string.semester_summer, year)
        Season.FALL -> context.getString(R.string.semester_summer, year)
        Season.WINTER -> context.getString(R.string.semester_summer, year)
        Season.BLANK -> year.toString()
    }
}