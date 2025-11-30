package com.example.sgdh.data.remote

import android.content.Context
import com.example.sgdh.data.local.PreferencesManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://sgdh.systems/api/" // CAMBIAR ESTO

    private var retrofit: Retrofit? = null
    private lateinit var preferencesManager: PreferencesManager

    fun initialize(context: Context) {
        preferencesManager = PreferencesManager(context)
    }

    private fun getClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val token = preferencesManager.getToken()
            val requestBuilder = chain.request().newBuilder()

            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            requestBuilder.addHeader("Accept", "application/json")
            requestBuilder.addHeader("Content-Type", "application/json")

            chain.proceed(requestBuilder.build())
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun getInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    val apiService: ApiService by lazy {
        getInstance().create(ApiService::class.java)
    }
}