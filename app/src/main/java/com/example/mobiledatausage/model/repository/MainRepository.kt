package com.example.mobiledatausage.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.mobiledatausage.model.MobileDataUsage

interface MainRepository {
    suspend fun getDataUsage(resourceId: String, limit: String): MobileDataUsage?
}