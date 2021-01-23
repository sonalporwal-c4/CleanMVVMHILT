package com.cleanmvvm.starwars.data.repository

import com.cleanmvvm.starwars.data.dto.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface StarWarClient {

    @ExperimentalCoroutinesApi
    suspend fun searchCharacterByrName(characterName: String): CharacterSearchDTO

    @ExperimentalCoroutinesApi
    suspend fun  getCharacterDetail(url: String): CharacterDetailsDTO

    @ExperimentalCoroutinesApi
    suspend fun  getPlanetDetails(url: String): PlanetDetailsDTO

    @ExperimentalCoroutinesApi
    suspend fun  getSpeciesDetails(url: String): SpeciesDetailsDTO

    @ExperimentalCoroutinesApi
    suspend fun  getFilmsDetails(url: String): MoviesDetailsDTO
}