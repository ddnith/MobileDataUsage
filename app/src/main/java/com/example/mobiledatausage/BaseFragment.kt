package com.example.mobiledatausage

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    fun showLoadingAndMessage(showLoading: Boolean = false, message: String? = null) {
        activity?.let {
            with(activity as MainActivity) {
                if (showLoading) showLoading() else hideLoading()
                message?.let { showMessage(it) }
            }
        }
    }
}