package com.wafflestudio.snuev.view.profile.fragment.myevaluations

import android.arch.lifecycle.MutableLiveData
import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ItemMyEvaluationBinding
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_my_evaluation.view.*

class MyEvaluationAdapter(
        private val fragment: Fragment,
        items: MutableLiveData<List<Evaluation>>,
        private val api: SnuevApi,
        private val onItemClick: (Evaluation) -> Unit
) : BaseAdapter<Evaluation, MyEvaluationAdapter.EvaluationHolder>(fragment, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvaluationHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_my_evaluation, parent, false)
        return EvaluationHolder(view, fragment, this, api, onItemClick)
    }

    var itemsClickable = true

    class EvaluationHolder(
            view: View,
            fragment: Fragment,
            private val adapter: MyEvaluationAdapter,
            api: SnuevApi,
            private val onItemClick: (Evaluation) -> Unit
    ) : BaseViewHolder<Evaluation>(view) {
        private lateinit var evaluation: Evaluation
        private val viewModel = MyEvaluationItemViewModel(fragment, api)
        private val binding by lazy { DataBindingUtil.bind<ItemMyEvaluationBinding>(view) }

        override fun bind(data: Evaluation, position: Int) {
            evaluation = data
            binding?.viewmodel = viewModel
            binding?.evaluation = evaluation
            binding?.lectureId = evaluation.getLecture()?.id ?: ""

            view.text_lecture_name.text = evaluation.getLecture()?.name ?: ""
            view.text_lecture_detail.text = context.getString(
                    R.string.lecture_details,
                    evaluation.getLecture()?.getCourse()?.getDepartment()?.name ?: "",
                    evaluation.getLecture()?.getCourse()?.targetGrade ?: "",
                    evaluation.getLecture()?.getProfessor()?.name ?: ""
            )

            view.clicks().subscribe {
                if (adapter.itemsClickable) {
                    onItemClick(data)
                }
            }
        }
    }
}