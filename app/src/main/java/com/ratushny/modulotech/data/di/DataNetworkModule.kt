package com.ratushny.modulotech.data.di

import com.ratushny.modulotech.BuildConfig
import com.ratushny.modulotech.data.network.ModuloService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 20L

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BuildConfig.BASE_STORAGE_URL)
            .client(get())
            .build()
            .create(ModuloService::class.java)
    }
}
