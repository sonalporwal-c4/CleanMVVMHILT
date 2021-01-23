package com.cleanmvvm.starwars.domain.repository

import com.cleanmvvm.starwars.domain.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface StarWarCache {

    @ExperimentalCoroutinesApi
    suspend fun cacheCharacterByName(data: Pair<String, CharacterSearchModel>)

    @ExperimentalCoroutinesApi
    suspend fun getCharacterByName(characterName: String): CharacterSearchModel?

    @ExperimentalCoroutinesApi
    suspend fun  cacheCharacterDetail(detailsData: Pair<String, CharacterDetailsModel>)

    @ExperimentalCoroutinesApi
    suspend fun  getCharacterDetail(url: String): CharacterDetailsModel?

    @ExperimentalCoroutinesApi
    suspend fun  cachePlanetDetail(detailsData: Pair<String, PlanetDetailsModel>)

    @ExperimentalCoroutinesApi
    suspend fun  getPlanetDetail(url: String): PlanetDetailsModel?

    @ExperimentalCoroutinesApi
    suspend fun  cacheMoviesDetail(detailsData: Pair<String, MoviesDetailsModel>)

    @ExperimentalCoroutinesApi
    suspend fun  getMoviesDetail(url: String): MoviesDetailsModel?

    @ExperimentalCoroutinesApi
    suspend fun  cacheSpeciesDetail(detailsData: Pair<String, SpeciesDetailsModel>)

    @ExperimentalCoroutinesApi
    suspend fun  getSpeciesDetail(url: String): SpeciesDetailsModel?

}
