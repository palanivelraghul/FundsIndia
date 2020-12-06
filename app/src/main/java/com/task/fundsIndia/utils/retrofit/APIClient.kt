package com.task.fundsIndia.utils.retrofit

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {
    private val httpClient = OkHttpClient().newBuilder()

    fun getClient(url: String): Retrofit? {
        val builder: Retrofit.Builder = getBuilder(url)
        return builder.build()
    }

    private fun getBuilder(url: String): Retrofit.Builder {
        val gson: Gson = GsonBuilder().setLenient().disableHtmlEscaping().create()
        return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson))
    }

    fun createService(url: String, authToken: String?): Retrofit? {
        var retrofit: Retrofit? = null
        httpClient.connectTimeout(1, TimeUnit.MINUTES).readTimeout(90, TimeUnit.SECONDS).build()
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken)
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
                val builder: Retrofit.Builder = getBuilder(url)
                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }
        return retrofit
    }
}