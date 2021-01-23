package com.cleanmvvm.starwars.domain.repository

import com.cleanmvvm.starwars.domain.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface StarWarRepository {

    @ExperimentalCoroutinesApi
    suspend fun searchCharacterByName(characterName: String): CharacterSearchModel

    @ExperimentalCoroutinesApi
    suspend fun  getCharacterDetail(url: String): CharacterDetailsModel

    @ExperimentalCoroutinesApi
    suspend fun  getPlanetDetails(url: String): PlanetDetailsModel

    @ExperimentalCoroutinesApi
    suspend fun  getSpeciesDetails(url: String): SpeciesDetailsModel

    @ExperimentalCoroutinesApi
    suspend fun  getFilmsDetails(url: String): MoviesDetailsModel
}