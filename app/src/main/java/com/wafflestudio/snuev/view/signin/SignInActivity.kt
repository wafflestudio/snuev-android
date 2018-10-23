package com.wafflestudio.snuev.view.signin

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivitySignInBinding
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.main.MainActivity
import com.wafflestudio.snuev.view.signup.SignUpActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignInActivity : BaseActivity() {
    companion object {
        const val TAG = "_SignIn"
        const val REQUEST_CODE = 1

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[SignInViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.viewmodel = viewModel

        createObservers()
        setupEvents()
    }

    override fun onResume() {
        super.onResume()
        button_to_sign_up.isEnabled = true
    }

    private fun createObservers() {
        viewModel.user.observe(this, Observer { user ->
            user?.let {
                MainActivity.startActivity(this)
                finish()
            }
        })

        viewModel.usernameFieldEmpty.observe(this, Observer { isEmpty ->
            edit_layout_email.error = if (isEmpty == true) {
                getString(R.string.error_username_empty)
            } else {
                null
            }
        })

        viewModel.passwordFieldEmpty.observe(this, Observer { isEmpty ->
            edit_layout_password.error = if (isEmpty == true) {
                getString(R.string.error_password_empty)
            } else {
                null
            }
        })

        viewModel.passwordTooShort.observe(this, Observer { isTooShort ->
            edit_layout_password.error = if (isTooShort == true) {
                getString(R.string.error_password_too_short)
            } else {
                null
            }
        })

        viewModel.invalidCredentials.observe(this, Observer { isInvalid ->
            edit_layout_password.error = if (isInvalid == true) {
                getString(R.string.error_invalid_credentials)
            } else {
                null
            }
        })
    }

    private fun setupEvents() {
        button_to_sign_up.clicks().subscribe {
            button_to_sign_up.isEnabled = false
            SignUpActivity.startActivity(this)
        }
    }
}