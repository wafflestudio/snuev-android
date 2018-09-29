package com.wafflestudio.snuev.view.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {
    companion object {
        const val TAG = "_Profile"
        const val REQUEST_CODE = 6

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, ProfileActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupPager()
        setupViews()
        setupEvents()
    }

    override fun onResume() {
        super.onResume()
        btn_sign_out.isEnabled = true
    }

    private fun setupPager() {
        view_pager.adapter = ProfilePagerAdapter(this, supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun setupViews() {
        text_nickname.text = SnuevPreference.user?.nickname ?: ""
        val outerLayout = (tab_layout.getChildAt(0) as LinearLayout)
        for (index in 0..(outerLayout.childCount - 1)) {
            val layout = outerLayout.getChildAt(index) as LinearLayout
            val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layout.layoutParams = layoutParams
        }
    }

    private fun setupEvents() {
        btn_sign_out.clicks().subscribe {
            btn_sign_out.isEnabled = false
            application.signOut()
        }
    }
}