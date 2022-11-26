package com.example.mobiledatausage.model

import com.google.gson.annotations.SerializedName

data class MobileDataUsageAnnual (
    val title: String,
    val dataUsage:Double,
)

data class MobileDataUsage (
    val help: String,
    val success: Boolean,
    val result: Result
)

data class Result (
    @SerializedName("resource_id")
    val resourceID: String,

    val fields: List<Field>,
    val records: List<Record>,

    @SerializedName("_links")
    val links: Links,

    val limit: Long,
    val total: Long
)

data class Field (
    val type: String,
    val id: String
)

data class Links (
    val start: String,
    val next: String
)

data class Record (
    @SerializedName("volume_of_mobile_data")
    val volumeOfMobileData: String,

    val quarter: String,

    @SerializedName("_id")
    val id: Long
)