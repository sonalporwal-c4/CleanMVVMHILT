package com.cleanmvvm.starwars.domain.interactors

import com.cleanmvvm.starwars.domain.repository.StarWarRepository
import com.cleanmvvm.starwars.domain.base.IUseCase
import com.cleanmvvm.starwars.domain.model.PlanetDetailsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPlanetDetailsUseCase @Inject constructor(private val repository: StarWarRepository)
: IUseCase<String, PlanetDetailsModel> {
    @ExperimentalCoroutinesApi
    override suspend fun execute(input: String): PlanetDetailsModel {
       return repository.getPlanetDetails(input)
    }

}