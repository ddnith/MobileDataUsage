package com.example.mobiledatausage.model.repository

import android.util.Log
import com.example.mobiledatausage.model.ApiInterface
import com.example.mobiledatausage.model.MobileDataUsage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainRepositoryImpl(private val api: ApiInterface): MainRepository {
    override suspend fun getDataUsage(resourceId: String, limit: String): MobileDataUsage? {
        val result = api.getMobileDataUsage(resourceId, limit)
        return result.body()
    }
}