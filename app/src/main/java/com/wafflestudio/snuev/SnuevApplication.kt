package com.wafflestudio.snuev

import android.app.Application
import android.content.Intent
import com.squareup.leakcanary.LeakCanary
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.view.signin.SignInActivity

class SnuevApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        SnuevPreference.init(this)
        SnuevApi.init(this)
    }

    fun signOut() {
        SnuevPreference.clear()
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}