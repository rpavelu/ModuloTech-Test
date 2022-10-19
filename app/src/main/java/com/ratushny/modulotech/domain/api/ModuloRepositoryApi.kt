package com.ratushny.modulotech.domain.api

interface ModuloRepositoryApi {

    suspend fun refreshData(force: Boolean)
}