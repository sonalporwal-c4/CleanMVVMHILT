package com.cleanmvvm.starwars.domain.interactors

import com.cleanmvvm.starwars.domain.repository.StarWarRepository
import com.cleanmvvm.starwars.domain.base.IUseCase
import com.cleanmvvm.starwars.domain.model.CharacterSearchModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSearchCharacterByNameUseCase @Inject constructor(private val repository: StarWarRepository)
    : IUseCase<String, CharacterSearchModel> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: String): CharacterSearchModel {
        return repository.searchCharacterByName(input)
    }
}