package com.example.mobiledatausage.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.mobiledatausage.model.MobileDataUsage
import com.example.mobiledatausage.utils.Resource

interface MainRepository {
    suspend fun getDataUsage(resourceId: String, limit: String): Resource<MobileDataUsage>
}