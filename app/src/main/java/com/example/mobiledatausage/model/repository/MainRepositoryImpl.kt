package com.example.mobiledatausage.model.repository

import com.example.mobiledatausage.model.ApiInterface
import com.example.mobiledatausage.model.MobileDataUsage

class MainRepositoryImpl(private val api: ApiInterface): MainRepository {
    override suspend fun getDataUsage(resourceId: String, limit: String): MobileDataUsage? {
        val result = api.getMobileDataUsage(resourceId, limit)
        return result.body()
    }
}