package com.example.mobiledatausage.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Objects

interface ApiInterface {

    @GET("datastore_search")
    suspend fun getMobileDataUsage(@Query("resource_id") resourceId: String, @Query("limit") limit: String) : Response<MobileDataUsage>
}