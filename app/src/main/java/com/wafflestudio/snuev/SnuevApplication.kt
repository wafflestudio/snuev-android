package com.wafflestudio.snuev

import android.app.Application
import com.wafflestudio.snuev.preference.SnuevPreference

class SnuevApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SnuevPreference.init(this)
    }
}