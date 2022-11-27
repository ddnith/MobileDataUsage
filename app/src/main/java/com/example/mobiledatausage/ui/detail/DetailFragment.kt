package com.example.mobiledatausage.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.viewpager2.widget.ViewPager2
import com.example.mobiledatausage.BaseFragment
import com.example.mobiledatausage.R
import com.example.mobiledatausage.tracking.Tracker
import com.example.mobiledatausage.utils.Resource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment() {

    private var position = 0
    private val detailViewModel by viewModel<DetailViewModel>()
    private lateinit var viewPager: ViewPager2

    private val viewModel by viewModel<DetailViewModel>()
    private val tracker: Tracker by inject()

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
        showLoadingAndMessage(true, "Loading data")
        detailViewModel.getMobileDataUsage()
        viewModel.observeAnnualLiveData().observe(viewLifecycleOwner) { response ->
            showLoadingAndMessage(false)
            if (response is Resource.Success) {
                with(viewPager) {
                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            tracker.onPageSeenEvent(response.data!![position])
                        }
                    })
                    adapter = ViewPager2Adapter(response.data!!)
                    doOnAttach { setCurrentItem(position, false) }
                }
            } else {
                showLoadingAndMessage(message = response.message)
            }
        }
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