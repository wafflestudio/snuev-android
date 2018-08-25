package com.wafflestudio.snuev.view.signin

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivitySignInBinding
import com.wafflestudio.snuev.view.base.BaseActivity

class SignInActivity : BaseActivity() {
    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.viewmodel = viewModel
    }
}