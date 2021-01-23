package com.cleanmvvm.starwars.domain.base

import kotlinx.coroutines.ExperimentalCoroutinesApi

interface IUseCase<in I : Any, out O : Any> {

    @ExperimentalCoroutinesApi
    suspend fun execute(input: I): O
}
