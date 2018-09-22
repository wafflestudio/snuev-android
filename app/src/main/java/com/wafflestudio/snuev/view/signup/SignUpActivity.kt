package com.wafflestudio.snuev.view.signup

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.ArrayAdapter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.focusChanges
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivitySignUpBinding
import com.wafflestudio.snuev.extension.visible
import com.wafflestudio.snuev.view.adapter.DepartmentAdapter
import com.wafflestudio.snuev.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    companion object {
        const val TAG = "_SignUp"
        const val REQUEST_CODE = 7

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.viewmodel = viewModel

        createObservers()
        setupEvents()
        setupRecyclerViews()
    }

    private fun createObservers() {
        viewModel.user.observe(this, Observer {
            it?.let { user ->
                SignUpCompleteActivity.startActivity(this, user.email)
                finish()
            }
        })
    }

    private fun setupEvents() {
        button_to_sign_in.clicks().subscribe {
            finish()
        }
        edit_department.focusChanges().subscribe { isFocused ->
            list_departments.visible = isFocused
        }
    }

    private fun setupRecyclerViews() {
        list_departments.layoutManager = LinearLayoutManager(this)
        list_departments.adapter = DepartmentAdapter(this, viewModel.departmentSearchResult) { department ->
            viewModel.selectDepartment(department)
            edit_department.clearFocus()
        }
    }
}