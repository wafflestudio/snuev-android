package com.wafflestudio.snuev.view.detail

import android.arch.lifecycle.MutableLiveData
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ItemDetailEvaluationBinding
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.view.base.BaseActivity
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder

class DetailEvaluationAdapter(
        private val activity: BaseActivity,
        items: MutableLiveData<List<Evaluation>>,
        private val lectureId: String
) : BaseAdapter<Evaluation, DetailEvaluationAdapter.EvaluationHolder>(activity, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvaluationHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_detail_evaluation, parent, false)
        return EvaluationHolder(view, activity, lectureId)
    }

    class EvaluationHolder(
            view: View,
            activity: BaseActivity,
            private val lectureId: String
    ) : BaseViewHolder<Evaluation>(view) {
        private val viewModel = DetailEvaluationItemViewModel(activity)
        private val binding by lazy { DataBindingUtil.bind<ItemDetailEvaluationBinding>(view) }

        override fun bind(data: Evaluation, position: Int) {
            viewModel.evaluation = data
            binding?.viewmodel = viewModel
            binding?.evaluation = data
            binding?.lectureId = lectureId
        }
    }
}