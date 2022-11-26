package com.example.mobiledatausage.model

import android.content.Context
import com.example.mobiledatausage.isInternetAvailable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitHelper(private val context: Context) {

    private val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
    private val cache = Cache(context.cacheDir, cacheSize)

    private val baseUrl = "https://data.gov.sg/api/action/"

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private var onlineInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }

    private var offlineInterceptor = Interceptor { chain ->
        var request: Request = chain.request()
        if (!isInternetAvailable(context)) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        chain.proceed(request)
    }

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
        addInterceptor(offlineInterceptor)
        addNetworkInterceptor(onlineInterceptor)
        cache(cache)
    }.build()


    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .client(client)
            .build()
    }
}