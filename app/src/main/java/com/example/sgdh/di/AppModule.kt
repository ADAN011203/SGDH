package com.example.sgdh.di

import android.content.Context
import com.example.sgdh.data.api.PharmacyApiService
import com.example.sgdh.data.api.RetrofitClient
import com.example.sgdh.data.local.PreferencesManager
import com.example.sgdh.data.repository.AuthRepository
import com.example.sgdh.data.repository.ProductoRepository
import com.example.sgdh.data.repository.SolicitudRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: PharmacyApiService,
        sharedPrefManager: PreferencesManager
    ): AuthRepository {
        return AuthRepository(apiService, sharedPrefManager)
    }

    @Provides
    @Singleton
    fun provideSolicitudRepository(apiService: PharmacyApiService): SolicitudRepository {
        return SolicitudRepository()
    }

    @Provides
    @Singleton
    fun provideProductoRepository(apiService: PharmacyApiService): ProductoRepository {
        return ProductoRepository()
    }

    @Provides
    @Singleton
    fun provideSharedPrefManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }
}