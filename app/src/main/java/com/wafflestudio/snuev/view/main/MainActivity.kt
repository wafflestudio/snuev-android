package com.wafflestudio.snuev.view.main

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivityMainBinding
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.profile.ProfileActivity
import com.wafflestudio.snuev.view.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bar_navigation.*

class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "_Main"
        const val REQUEST_CODE = 2

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel

        setupEvents()
        setupRecyclerViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLatestEvaluations()
        viewModel.fetchMostEvaluatedLectures()
        viewModel.fetchTopRatedLectures()
        viewModel.fetchMostLikedEvaluations()
    }

    private fun setupEvents() {
        button_search.clicks().subscribe { SearchActivity.startActivity(this) }
        button_profile.clicks().subscribe { ProfileActivity.startActivity(this) }
    }

    private fun setupRecyclerViews() {
        list_latest_evaluations.layoutManager = LinearLayoutManager(this)
        list_latest_evaluations.adapter = MainEvaluationAdapter(this, viewModel.latestEvaluations)
        list_most_evaluated_lectures.layoutManager = LinearLayoutManager(this)
        list_most_evaluated_lectures.adapter = MainLectureAdapter(this, viewModel.mostEvaluatedLectures)
        list_top_rated_lectures.layoutManager = LinearLayoutManager(this)
        list_top_rated_lectures.adapter = MainLectureAdapter(this, viewModel.topRatedLectures)
        list_most_liked_evaluations.layoutManager = LinearLayoutManager(this)
        list_most_liked_evaluations.adapter = MainEvaluationAdapter(this, viewModel.mostLikedEvaluations)
    }
}
