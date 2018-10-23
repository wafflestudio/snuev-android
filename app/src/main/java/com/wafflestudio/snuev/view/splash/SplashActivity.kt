package com.wafflestudio.snuev.view.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.main.MainActivity
import com.wafflestudio.snuev.view.signin.SignInActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {
    @Inject
    lateinit var preference: SnuevPreference
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Do not set content view, view is displayed from theme
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]

        preference.token?.let {
            viewModel.fetchUser()
        } ?: let {
            SignInActivity.startActivity(this)
        }

        createObservers()
    }

    private fun createObservers() {
        viewModel.user.observe(this, Observer { user ->
            user?.let {
                MainActivity.startActivity(this)
                finish()
            }
        })
        viewModel.unauthorized.observe(this, Observer { unauthorized ->
            if (unauthorized == true) {
                SignInActivity.startActivity(this)
            }
        })
    }
}