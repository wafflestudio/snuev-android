package com.wafflestudio.snuev.model.resource

import android.support.annotation.StringRes

data class Filter(
        @StringRes val resId: Int,
        var selected: Boolean = false
)