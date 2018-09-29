package com.wafflestudio.snuev.view.profile.fragment.bookmarkedlectures

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.view.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_list.*

class BookmarkedLecturesFragment : Fragment() {
    lateinit var viewModel: BookmarkedLecturesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookmarkedLecturesViewModel::class.java)
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = BookmarkedLectureAdapter(this, viewModel.bookmarkedLectures) { lecture ->
            activity?.let {
                DetailActivity.startActivity(it, lecture.id)
            }
        }
    }

    companion object {
        fun newInstance(): BookmarkedLecturesFragment =
                BookmarkedLecturesFragment()
    }
}