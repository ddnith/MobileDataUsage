package com.example.mobiledatausage.ui.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.mobiledatausage.databinding.FragmentMobileUsageListBinding
import com.example.mobiledatausage.model.MobileDataUsageAnnual

class MobileUsageRecyclerViewAdapter(
    private val values: List<MobileDataUsageAnnual>,
    private val onItemClicked: (MobileDataUsageAnnual) -> Unit
) : RecyclerView.Adapter<MobileUsageRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewHolder =
            FragmentMobileUsageListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(viewHolder) {onItemClicked(values[it])}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.yearView.text = item.year.toString()
        holder.dataVolumeView.text = item.dataUsage.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(
        binding: FragmentMobileUsageListBinding,
        private val onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener{
                onItemClicked(bindingAdapterPosition)
            }
        }
        val yearView: TextView = binding.itemNumber
        val dataVolumeView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + dataVolumeView.text + "'"
        }
    }

}