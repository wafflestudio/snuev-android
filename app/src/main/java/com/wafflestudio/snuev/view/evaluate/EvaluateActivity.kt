package com.wafflestudio.snuev.view.evaluate

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivityEvaluateBinding
import com.wafflestudio.snuev.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_evaluate.*
import javax.inject.Inject

class EvaluateActivity : BaseActivity() {
    companion object {
        const val TAG = "_Evaluate"
        const val REQUEST_CODE = 5
        const val EXTRA_LECTURE_ID = "$TAG/LECTURE_ID"
        const val EXTRA_LECTURE_NAME = "$TAG/LECTURE_NAME"

        fun startActivity(activity: Activity, lectureId: String, lectureName: String) {
            val intent = Intent(activity, EvaluateActivity::class.java)
            intent.putExtra(EXTRA_LECTURE_ID, lectureId)
            intent.putExtra(EXTRA_LECTURE_NAME, lectureName)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: EvaluateViewModel
    private lateinit var binding: ActivityEvaluateBinding

    private val lectureId: String
    get() = intent.getStringExtra(EXTRA_LECTURE_ID) ?: ""
    private val lectureName: String
    get() = intent.getStringExtra(EXTRA_LECTURE_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[EvaluateViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate)
        binding.viewmodel = viewModel
        binding.setLifecycleOwner(this)

        setupViews()
        setupEvents()
    }

    private fun setupViews() {
        text_lecture_name.text = lectureName
    }

    private fun setupEvents() {
        button_evaluate.clicks().subscribe {
            viewModel.createEvaluation(lectureId)
        }
        button_close.clicks().subscribe {
            finish()
        }
    }
}