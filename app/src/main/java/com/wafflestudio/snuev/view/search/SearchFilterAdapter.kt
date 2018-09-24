package com.wafflestudio.snuev.view.search

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.extension.visible
import com.wafflestudio.snuev.model.resource.Filter
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_search_filter.view.*

class SearchFilterAdapter(
        owner: LifecycleOwner,
        items: MutableLiveData<List<Filter>>
) : BaseAdapter<Filter, SearchFilterAdapter.FilterHolder>(owner, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_search_filter, parent, false)
        return FilterHolder(view, this)
    }

    class FilterHolder(
            view: View,
            val adapter: SearchFilterAdapter
    ) : BaseViewHolder<Filter>(view) {
        override fun bind(data: Filter, position: Int) {
            view.layout_item.background = if (data.selected) {
                view.text_filter_name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                ContextCompat.getDrawable(context, R.drawable.bg_cornered_primary_light)
            } else {
                view.text_filter_name.setTextColor(ContextCompat.getColor(context, R.color.black_70))
                ContextCompat.getDrawable(context, R.color.white)
            }
            view.text_filter_name.text = context.getText(data.resId)
            view.image_check.visible = data.selected
            view.clicks().subscribe {
                data.selected = !data.selected
                adapter.notifyItemChanged(position)
            }
        }
    }
}