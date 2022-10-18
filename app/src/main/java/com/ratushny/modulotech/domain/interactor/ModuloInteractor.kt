package com.ratushny.modulotech.domain.interactor

import com.ratushny.modulotech.domain.api.ModuloRepositoryApi

class ModuloInteractor(
    private val repository: ModuloRepositoryApi
) {
    suspend fun loadData() = repository.loadData()

    suspend fun forceRefreshData() = repository.forceRefreshData()
}