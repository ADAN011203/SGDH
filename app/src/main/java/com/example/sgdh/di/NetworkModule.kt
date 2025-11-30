package com.example.sgdh.di

import com.example.sgdh.data.api.PharmacyApiService
import com.example.sgdh.data.api.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providePharmacyApiService(): PharmacyApiService {
        return RetrofitClient.getApiService()
    }
}