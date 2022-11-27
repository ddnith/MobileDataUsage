package com.example.mobiledatausage.repository

import com.example.mobiledatausage.model.Links
import com.example.mobiledatausage.model.MobileDataUsage
import com.example.mobiledatausage.model.Record
import com.example.mobiledatausage.model.Result
import com.example.mobiledatausage.model.repository.MainRepository
import com.example.mobiledatausage.utils.Resource

class FakeRepository: MainRepository {

    var shouldReturnNetworkError = false

    override suspend fun getDataUsage(
        resourceId: String,
        limit: String
    ): Resource<MobileDataUsage> {
        return if (!shouldReturnNetworkError) {
            Resource.Success(
                MobileDataUsage("",
                    true,
                    Result(
                        "",
                        emptyList(),
                        listOf(
                            Record("0.000384","2004-Q3",1),
                            Record("0.000543","2004-Q3",2),
                            Record("0.000634","2005-Q1",3),
                        ),
                        Links("",""),
                        1L,
                        1L
                    )
                )
            )
        } else {
            Resource.Error("Error")
        }
    }
}