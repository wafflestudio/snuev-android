package com.wafflestudio.snuev.view.search

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ItemSelectedDepartmentBinding
import com.wafflestudio.snuev.model.resource.Department
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder

class SearchSelectedDepartmentAdapter(
        owner: LifecycleOwner,
        items: MutableLiveData<List<Department>>,
        private val onCancelClick: (Department) -> Unit
) : BaseAdapter<Department, SearchSelectedDepartmentAdapter.DepartmentHolder>(owner, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_selected_department, parent, false)
        return DepartmentHolder(view, onCancelClick)
    }

    class DepartmentHolder(
            view: View,
            private val onCancelClick: (Department) -> Unit
    ) : BaseViewHolder<Department>(view) {
        private val binding by lazy { DataBindingUtil.bind<ItemSelectedDepartmentBinding>(view) }

        override fun bind(data: Department, position: Int) {
            binding?.department = data
            binding?.buttonCancel?.clicks()?.subscribe {
                onCancelClick(data)
            }
        }
    }
}