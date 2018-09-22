package com.wafflestudio.snuev.view.adapter

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.model.resource.Department
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_department.view.*

class DepartmentAdapter(
        owner: LifecycleOwner,
        items: MutableLiveData<List<Department>>,
        private val onItemClick: (Department) -> Unit
) : BaseAdapter<Department, DepartmentAdapter.DepartmentHolder>(owner, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_department, parent, false)
        return DepartmentHolder(view, onItemClick)
    }

    class DepartmentHolder(
            view: View,
            private val onItemClick: (Department) -> Unit
    ) : BaseViewHolder<Department>(view) {
        private lateinit var department: Department

        override fun bind(data: Department) {
            department = data
            view.text_department_name.text = department.name

            view.clicks().subscribe { onItemClick(data) }
        }
    }
}