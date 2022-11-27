package com.example.mobiledatausage.model.repository

import com.example.mobiledatausage.model.ApiInterface
import com.example.mobiledatausage.model.MobileDataUsage
import com.example.mobiledatausage.utils.Resource

class MainRepositoryImpl(private val api: ApiInterface): MainRepository {
    override suspend fun getDataUsage(resourceId: String, limit: String): Resource<MobileDataUsage> {
        return try {
            val response = api.getMobileDataUsage(resourceId, limit)
            val result =  response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: java.lang.Exception) {
            Resource.Error(e.message?:"An error occurred")
        }
    }
}