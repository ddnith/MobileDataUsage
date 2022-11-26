package com.example.mobiledatausage.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobiledatausage.model.MobileDataUsage
import com.example.mobiledatausage.model.MobileDataUsageAnnual
import com.example.mobiledatausage.model.Record
import com.example.mobiledatausage.model.repository.MainRepository
import com.example.mobiledatausage.roundTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val mutableAnnualLiveData = MutableLiveData<List<Pair<String, List<Record>>>>()
    fun observeAnnualLiveData(): LiveData<List<Pair<String, List<Record>>>> = mutableAnnualLiveData

    fun getMobileDataUsage() {
        GlobalScope.launch {
            val dataUsage = mainRepository.getDataUsage("a807b7ab-6cad-4aa6-87d0-e283a7353a0f", "100")
            if (dataUsage != null) {
                withContext(Dispatchers.Main) {
                    val combinedData = combineDataUsageAnnually(dataUsage)
                    mutableAnnualLiveData.value = combinedData
                    combineDataUsageAnnually(dataUsage)
                }
            }
        }
    }

    private fun combineDataUsageAnnually(dataUsage: MobileDataUsage): List<Pair<String, List<Record>>> {
        return dataUsage.result.records
            .groupBy { record -> record.quarter.substring(0..3) }
            .toList().sortedByDescending { it.first }
    }

}