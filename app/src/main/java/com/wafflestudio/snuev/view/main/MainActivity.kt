package com.wafflestudio.snuev.view.main

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivityMainBinding
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.detail.DetailActivity
import com.wafflestudio.snuev.view.profile.ProfileActivity
import com.wafflestudio.snuev.view.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bar_navigation.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "_Main"
        const val REQUEST_CODE = 2

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private var latestEvaluationAdapter: MainEvaluationAdapter? = null
    private var mostEvaluatedLectureAdapter: MainLectureAdapter? = null
    private var topRatedLectureAdapter: MainLectureAdapter? = null
    private var mostLikedEvaluationAdapter: MainEvaluationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel

        setupEvents()
        setupRecyclerViews()
    }

    override fun onResume() {
        super.onResume()
        button_search.isEnabled = true
        button_profile.isEnabled = true
        latestEvaluationAdapter?.itemsClickable = true
        mostEvaluatedLectureAdapter?.itemsClickable = true
        topRatedLectureAdapter?.itemsClickable = true
        mostLikedEvaluationAdapter?.itemsClickable = true
        viewModel.fetchLatestEvaluations()
        viewModel.fetchMostEvaluatedLectures()
        viewModel.fetchTopRatedLectures()
        viewModel.fetchMostLikedEvaluations()
    }

    private fun setupEvents() {
        button_search.clicks().subscribe {
            button_search.isEnabled = false
            SearchActivity.startActivity(this)
        }
        button_profile.clicks().subscribe {
            button_profile.isEnabled = false
            ProfileActivity.startActivity(this)
        }
    }

    private fun setupRecyclerViews() {
        list_latest_evaluations.layoutManager = LinearLayoutManager(this)
        latestEvaluationAdapter = MainEvaluationAdapter(this, viewModel.latestEvaluations) { evaluation ->
            evaluation.getLecture()?.id?.let { id ->
                latestEvaluationAdapter?.itemsClickable = false
                DetailActivity.startActivity(this, id)
            }
        }
        list_latest_evaluations.adapter = latestEvaluationAdapter

        list_most_evaluated_lectures.layoutManager = LinearLayoutManager(this)
        mostEvaluatedLectureAdapter = MainLectureAdapter(this, viewModel.mostEvaluatedLectures) { lecture ->
            lecture.id?.let { id ->
                mostEvaluatedLectureAdapter?.itemsClickable = false
                DetailActivity.startActivity(this, id)
            }
        }
        list_most_evaluated_lectures.adapter = mostEvaluatedLectureAdapter

        list_top_rated_lectures.layoutManager = LinearLayoutManager(this)
        topRatedLectureAdapter = MainLectureAdapter(this, viewModel.topRatedLectures) { lecture ->
            lecture.id?.let { id ->
                topRatedLectureAdapter?.itemsClickable = false
                DetailActivity.startActivity(this, id)
            }
        }
        list_top_rated_lectures.adapter = topRatedLectureAdapter

        list_most_liked_evaluations.layoutManager = LinearLayoutManager(this)
        mostLikedEvaluationAdapter = MainEvaluationAdapter(this, viewModel.mostLikedEvaluations) { evaluation ->
            evaluation.getLecture()?.id?.let { id ->
                mostLikedEvaluationAdapter?.itemsClickable = false
                DetailActivity.startActivity(this, id)
            }
        }
        list_most_liked_evaluations.adapter = mostLikedEvaluationAdapter
    }
}
