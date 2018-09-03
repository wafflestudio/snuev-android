package com.wafflestudio.snuev.view.detail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivityDetailBinding
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.evaluate.EvaluateActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {
    companion object {
        const val TAG = "_Detail"
        const val REQUEST_CODE = 4
        const val EXTRA_LECTURE = "$TAG/LECTURE"

        fun startActivity(activity: Activity, lectureId: Int) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(EXTRA_LECTURE, lectureId)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private val lectureId: Int
    get() = intent.getIntExtra(EXTRA_LECTURE, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewmodel = viewModel
        binding.setLifecycleOwner(this)

        createObservables()
        setupEvents()
        setupRecyclerViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLecture(lectureId.toString())
        viewModel.fetchLectureEvaluations(lectureId.toString(), 0)
    }

    private fun createObservables() {
        viewModel.evaluations.observe(this, Observer {
            layout_no_evaluations.visibility = if (it?.isNotEmpty() == true) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        })
   }

    private fun setupEvents() {
        button_leave_review.clicks().subscribe {
            button_leave_review.isEnabled = false
            EvaluateActivity.startActivity(this, lectureId, viewModel.lectureName.value ?: "")
            button_leave_review.isEnabled = true
        }
    }

    private fun setupRecyclerViews() {
        list_evaluations.layoutManager = LinearLayoutManager(this)
        list_evaluations.adapter = DetailEvaluationAdapter(this, viewModel.evaluations)
    }
}