package com.wafflestudio.snuev.view.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(
        owner: LifecycleOwner,
        private val items: MutableLiveData<List<T>>
) : RecyclerView.Adapter<VH>() {
    init {
        items.observe(owner, Observer { notifyDataSetChanged() })
    }

    override fun getItemCount(): Int = items.value?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        items.value?.get(position)?.let { item ->
            holder.bind(item, position)
        }
    }
}