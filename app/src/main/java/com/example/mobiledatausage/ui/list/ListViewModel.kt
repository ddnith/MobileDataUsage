package com.example.mobiledatausage.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledatausage.model.MobileDataUsage
import com.example.mobiledatausage.model.MobileDataUsageAnnual
import com.example.mobiledatausage.model.repository.MainRepository
import com.example.mobiledatausage.roundTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val mainRepository: MainRepository): ViewModel() {


    private val mutableAnnualLiveData = MutableLiveData<List<MobileDataUsageAnnual>>()

    fun observeAnnualLiveData(): LiveData<List<MobileDataUsageAnnual>> = mutableAnnualLiveData

    fun getMobileDataUsage() {
        viewModelScope.launch(Dispatchers.IO) {
            val dataUsage = mainRepository.getDataUsage("a807b7ab-6cad-4aa6-87d0-e283a7353a0f", "100")
            if (dataUsage != null) {
                withContext(Dispatchers.Default) {
                    val combinedData = combineDataUsageAnnually(dataUsage)
                    mutableAnnualLiveData.postValue(combinedData)
                }
            }
        }
    }

    private fun combineDataUsageAnnually(dataUsage: MobileDataUsage): List<MobileDataUsageAnnual> {
        val map = mutableMapOf<Int, Double>()
        for (record in dataUsage.result.records) {
            val volume = record.volumeOfMobileData.toDouble()
            val year = record.quarter.substring(0..3).toInt()
            val current = map.getOrDefault(year, 0).toDouble()
            map[year] = current + volume
        }

        val result: MutableList<MobileDataUsageAnnual> = mutableListOf()
        for ((key,value ) in map) {
            result.add(MobileDataUsageAnnual(key.toString(), value.roundTo(3)))
        }
        result.sortByDescending { mobileDataUsageAnnual -> mobileDataUsageAnnual.title.toInt() }
        return result
    }
}