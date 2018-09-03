package com.wafflestudio.snuev.view.search

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jakewharton.rxbinding2.widget.textChanges
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ActivitySearchBinding
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.bar_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity() {
    companion object {
        const val TAG = "_Search"
        const val REQUEST_CODE = 3

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.viewmodel = viewModel

        createObservers()
        setupEvents()
        setupRecyclerViews()
    }

    private fun createObservers() {
        viewModel.searchResultLectures.observe(this, Observer {
            layout_no_search_result.visibility = if (it?.isNotEmpty() == true) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        })
    }

    private fun setupEvents() {
        edit_search.textChanges()
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter { it.isNotBlank() }
                .subscribe {
                    viewModel.searchLectures(it.toString())
                }
    }

    private fun setupRecyclerViews() {
        list_search_result_lectures.layoutManager = LinearLayoutManager(this)
        list_search_result_lectures.adapter = SearchLectureAdapter(this, viewModel.searchResultLectures) { lecture ->
            lecture.id?.let { lectureId ->
                DetailActivity.startActivity(this, lectureId)
            }
        }
    }
}