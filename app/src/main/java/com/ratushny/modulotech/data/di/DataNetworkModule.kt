package com.ratushny.modulotech.data.network

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_STORAGE_URL = "http://storage42.com/"
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
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_STORAGE_URL)
            .client(get())
            .build()
            .create(ModuloService::class.java)
    }
}
