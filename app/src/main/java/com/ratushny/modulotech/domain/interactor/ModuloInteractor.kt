package com.ratushny.modulotech.domain.interactor

import com.ratushny.modulotech.domain.api.ModuloRepositoryApi

class ModuloInteractor(
    private val repository: ModuloRepositoryApi
) {
    suspend fun loadData(force: Boolean) = repository.refreshData(force)

}