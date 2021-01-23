package com.cleanmvvm.starwars.domain.interactors

import com.cleanmvvm.starwars.domain.repository.StarWarRepository
import com.cleanmvvm.starwars.domain.base.IUseCase
import com.cleanmvvm.starwars.domain.model.SpeciesDetailsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSpicesDetailsUseCase @Inject constructor(private val repository: StarWarRepository)
: IUseCase<String, SpeciesDetailsModel> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: String): SpeciesDetailsModel {
        return repository.getSpeciesDetails(input)
    }
}