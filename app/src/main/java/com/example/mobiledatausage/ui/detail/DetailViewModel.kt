package com.example.mobiledatausage.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledatausage.model.MobileDataUsage
import com.example.mobiledatausage.model.Record
import com.example.mobiledatausage.model.repository.MainRepository
import com.example.mobiledatausage.utils.DispatcherProvider
import com.example.mobiledatausage.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
    ) : ViewModel() {
    val mutableAnnualLiveData = MutableLiveData<Resource<List<Pair<String, List<Record>>>>>()
    fun observeAnnualLiveData(): LiveData<Resource<List<Pair<String, List<Record>>>>> = mutableAnnualLiveData

    fun getMobileDataUsage() {
        viewModelScope.launch(dispatcherProvider.io) {
            val response = mainRepository.getDataUsage("a807b7ab-6cad-4aa6-87d0-e283a7353a0f", "100")
            if (response != null) {
                withContext(dispatcherProvider.default) {
                    if (response is Resource.Success) {
                        val combinedData = combineDataUsageAnnually(response.data!!)
                        mutableAnnualLiveData.postValue(Resource.Success(combinedData))
                    } else {
                        mutableAnnualLiveData.postValue(Resource.Error("Something went wrong. Please check your Internet Connection"))
                    }
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