package com.wafflestudio.snuev

import timber.log.Timber

class TimberLog {
    companion object {
        fun init() {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                            "C:%s:%s",
                            super.createStackElementTag(element),
                            element.lineNumber
                    )
                }
            })
        }
    }
}