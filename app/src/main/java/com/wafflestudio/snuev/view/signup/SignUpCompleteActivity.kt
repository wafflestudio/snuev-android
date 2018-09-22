package com.wafflestudio.snuev.view.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up_complete.*

class SignUpCompleteActivity : BaseActivity() {
    companion object {
        const val TAG = "_SignUpComplete"
        const val REQUEST_CODE = 8
        const val EXTRA_USERNAME = "$TAG/EXTRA_USERNAME"

        fun startActivity(activity: Activity, username: String) {
            val intent = Intent(activity, SignUpCompleteActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, username)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    val username: String
    get() = intent.getStringExtra(EXTRA_USERNAME) ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_complete)
        setupViews()
    }

    private fun setupViews() {
        text_username.text = username
    }
}