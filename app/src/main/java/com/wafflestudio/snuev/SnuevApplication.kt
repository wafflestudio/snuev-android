package com.wafflestudio.snuev

import android.app.Application
import android.content.Intent
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.view.signin.SignInActivity

class SnuevApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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