package com.example.mobiledatausage.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.mobiledatausage.R
import com.example.mobiledatausage.viewmodels.ListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A fragment representing a list of Items.
 */
class MobileUsageListFragment : Fragment() {

    private var columnCount = 1
    private val listViewModel by viewModel<ListViewModel>()
    private lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        listViewModel.getMobileDataUsage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mobile_usage_list_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            list = view
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel.observeAnnualLiveData().observe(viewLifecycleOwner, Observer {
            with(list) {
                adapter = MobileUsageRecyclerViewAdapter(it) {
                        mobileDataUsageAnnual ->
                    Log.d("DEEPAK", "${mobileDataUsageAnnual.year} -> ${mobileDataUsageAnnual.dataUsage}" )
                }
            }
        })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MobileUsageListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}