package com.cleanmvvm.starwars.domain.interactors

import com.cleanmvvm.starwars.domain.repository.StarWarRepository
import com.cleanmvvm.starwars.domain.base.IUseCase
import com.cleanmvvm.starwars.domain.model.CharacterDetailsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharacterDetailsUseCase @Inject constructor(private val repository: StarWarRepository)
: IUseCase<String, CharacterDetailsModel> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: String): CharacterDetailsModel {
        return repository.getCharacterDetail(input)
    }
}