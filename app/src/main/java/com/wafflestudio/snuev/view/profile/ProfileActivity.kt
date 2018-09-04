package com.wafflestudio.snuev.view.profile

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivityProfileBinding
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

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewmodel = viewModel

        setupPager()
        setupViews()
    }

    private fun setupPager() {
        view_pager.adapter = ProfilePagerAdapter(this, supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun setupViews() {
        text_nickname.text = SnuevPreference.user?.nickname ?: ""
    }
}