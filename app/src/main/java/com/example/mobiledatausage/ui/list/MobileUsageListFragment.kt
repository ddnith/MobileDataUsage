package com.example.mobiledatausage.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.mobiledatausage.MainActivity
import com.example.mobiledatausage.R
import com.example.mobiledatausage.ui.detail.DetailFragment
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
                        position ->
                    activity?.let {
                        with(activity as MainActivity){
                            navigateToFragment(DetailFragment.newInstance(position), true)
                        }
                    }
                }
            }
        })
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MobileUsageListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}