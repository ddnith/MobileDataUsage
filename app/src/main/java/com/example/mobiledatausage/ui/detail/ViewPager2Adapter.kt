package com.example.mobiledatausage.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiledatausage.databinding.FragmentMobileUsageListListBinding
import com.example.mobiledatausage.model.MobileDataUsageAnnual
import com.example.mobiledatausage.model.Record
import com.example.mobiledatausage.roundTo
import com.example.mobiledatausage.ui.list.MobileUsageRecyclerViewAdapter

class ViewPager2Adapter(private val values: List<Pair<String, List<Record>>>) : RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder =
            FragmentMobileUsageListListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = values[position].second
        val list = mutableListOf<MobileDataUsageAnnual>()
        for (item in value) {
            list.add(MobileDataUsageAnnual(item.quarter, item.volumeOfMobileData.toDouble().roundTo(3)))
        }
        holder.recyclerView.adapter = MobileUsageRecyclerViewAdapter(list){}
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(binding: FragmentMobileUsageListListBinding,) :
        RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.list
    }
}