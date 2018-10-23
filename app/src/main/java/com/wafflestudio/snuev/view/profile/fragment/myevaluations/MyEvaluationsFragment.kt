package com.wafflestudio.snuev.view.profile.fragment.myevaluations

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.view.base.BaseFragment
import com.wafflestudio.snuev.view.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class MyEvaluationsFragment : BaseFragment() {
    @Inject
    lateinit var api: SnuevApi
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MyEvaluationsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MyEvaluationsViewModel::class.java]
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = MyEvaluationAdapter(this, viewModel.myEvaluations, api) { evaluation ->
            evaluation.getLecture()?.let { lecture ->
                activity?.let {
                    DetailActivity.startActivity(it, lecture.id)
                }
            }
        }
    }

    companion object {
        fun newInstance(): MyEvaluationsFragment = MyEvaluationsFragment()
    }
}
