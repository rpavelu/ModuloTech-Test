package com.ratushny.modulotech.domain.api

interface ModuloRepositoryApi {
    suspend fun loadData()
    suspend fun forceRefreshData()
}