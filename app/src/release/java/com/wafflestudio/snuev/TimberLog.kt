package com.wafflestudio.snuev

import timber.log.Timber

class TimberLog {
    companion object {
        fun init() {
            Timber.plant()
        }
    }
}