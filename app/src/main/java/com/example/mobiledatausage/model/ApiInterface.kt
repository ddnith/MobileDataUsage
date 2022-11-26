package com.example.mobiledatausage.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Objects

interface ApiInterface {

    @GET("datastore_search")
    suspend fun getMobileDataUsage(@Query("resource_id") resourceId: String, @Query("limit") limit: String) : Response<MobileDataUsage>

//    @GET("latest/{countryCode}")
//    suspend fun getLatest(@Path("countryCode") countryCode: String) : Response<LatestRates>
//
//    @GET("pair/{base_code}/{target_code}/{amount}")
//    suspend fun getPairAmount(
//        @Path("base_code") baseCode: String,
//        @Path("target_code") targetCode: String,
//        @Path("amount") amount: String
//    ) : Response<PairAmount>
}