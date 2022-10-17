package com.ratushny.modulotech.data.network

import com.ratushny.modulotech.data.network.model.ServerResponse
import retrofit2.http.GET

interface ModuloService {

    @GET("modulotest/data.json")
    suspend fun loadData(): ServerResponse
}