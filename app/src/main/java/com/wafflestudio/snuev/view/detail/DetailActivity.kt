package com.wafflestudio.snuev.view.detail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivityDetailBinding
import com.wafflestudio.snuev.extension.visible
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.evaluate.EvaluateActivity
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity() {
    companion object {
        const val TAG = "_Detail"
        const val REQUEST_CODE = 4
        const val EXTRA_LECTURE = "$TAG/LECTURE"

        fun startActivity(activity: Activity, lectureId: String) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(EXTRA_LECTURE, lectureId)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    @Inject
    lateinit var api: SnuevApi
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private val lectureId: String
        get() = intent.getStringExtra(EXTRA_LECTURE) ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[DetailViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewmodel = viewModel
        binding.setLifecycleOwner(this)

        createObservables()
        setupEvents()
        setupRecyclerViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLecture(lectureId)
        viewModel.fetchLectureEvaluations(lectureId, 0)
    }

    private fun createObservables() {
        viewModel.evaluations.observe(this, Observer {
            layout_no_evaluations.visible = it?.isEmpty() == true
        })
    }

    private fun setupEvents() {
        toggle_bookmark.clicks().subscribe { viewModel.toggleBookmark(lectureId) }

        button_leave_review.clicks().subscribe {
            button_leave_review.isEnabled = false
            EvaluateActivity.startActivity(this, lectureId, viewModel.lectureName.value ?: "")
            button_leave_review.isEnabled = true
        }
    }

    private fun setupRecyclerViews() {
        list_evaluations.layoutManager = LinearLayoutManager(this)
        list_evaluations.adapter = DetailEvaluationAdapter(this, viewModel.evaluations, lectureId, api)
    }
}