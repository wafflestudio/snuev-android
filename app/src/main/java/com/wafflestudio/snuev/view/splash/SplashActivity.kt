package com.wafflestudio.snuev.view.splash

import android.content.Intent
import android.os.Bundle
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.main.MainActivity
import com.wafflestudio.snuev.view.signin.SignInActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Do not set content view, view is displayed from theme
        val intent = if (false /* authenticated */) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, SignInActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
