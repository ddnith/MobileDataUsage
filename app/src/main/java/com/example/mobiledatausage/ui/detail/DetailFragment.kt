package com.example.mobiledatausage.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.mobiledatausage.R
import com.example.mobiledatausage.model.MobileDataUsageAnnual
import com.example.mobiledatausage.ui.list.MobileUsageListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.FieldPosition

class DetailFragment : Fragment() {

    private var position = 0
    private val detailViewModel by viewModel<DetailViewModel>()
    private lateinit var viewPager: ViewPager2

    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewpager)
        detailViewModel.getMobileDataUsage()
        viewModel.observeAnnualLiveData().observe(viewLifecycleOwner, Observer {
            with(viewPager) {
                adapter = ViewPager2Adapter(it)
                doOnAttach { setCurrentItem(position, false) }
            }
        })
    }

    companion object {
        const val ARG_POSITION = "position"
        fun newInstance(position: Int) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, position)
            }
        }
    }
}