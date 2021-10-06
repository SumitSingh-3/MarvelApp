package com.marvelapp.domain

import com.marvelapp.BuildConfig
import com.marvelapp.base.App
import com.marvelapp.utils.AppUtil.hasNetwork
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object APIClient {


    val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
    val cache = Cache(App.INSTANCE.cacheDir, cacheSize)

    var offlineInterceptor: Interceptor = Interceptor { chain ->
        var request: Request = chain.request()
        if (!hasNetwork()!!) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        chain.proceed(request)
    }

    var onlineInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(APIInterceptor()).addInterceptor(interceptor)
            .addNetworkInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient())
        .build()

    fun getClient(): IApiService {
        return retrofit.create(IApiService::class.java)
    }
}