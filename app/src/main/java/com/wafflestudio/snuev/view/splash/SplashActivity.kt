package com.wafflestudio.snuev.views.splash

import android.content.Intent
import android.os.Bundle
import com.wafflestudio.snuev.views.base.BaseActivity
import com.wafflestudio.snuev.views.main.MainActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Do not set content view, view is displayed from theme
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}
