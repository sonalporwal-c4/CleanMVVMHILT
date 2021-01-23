package com.cleanmvvm.starwars.data.repository

import com.cleanmvvm.starwars.data.base.FailedRequest
import com.cleanmvvm.starwars.data.dto.toDomainModel
import com.cleanmvvm.starwars.domain.model.*
import com.cleanmvvm.starwars.domain.repository.StarWarCache
import com.cleanmvvm.starwars.domain.repository.StarWarRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarRepositoryImpl @Inject constructor(
        private val starWarCache: StarWarCache,
        private val starWarClient: StarWarClient
): StarWarRepository {

    @ExperimentalCoroutinesApi
    override suspend fun searchCharacterByName(characterName: String): CharacterSearchModel {
        try {
            if (starWarCache.getCharacterByName(characterName) == null ) {
                val characterDto = starWarClient.searchCharacterByrName(characterName)
                val characterModel = characterDto.toDomainModel()
                starWarCache.cacheCharacterByName( Pair(characterName, characterModel))
                return characterModel
            }
            return starWarCache.getCharacterByName(characterName)!!
        } catch (exception: FailedRequest) {
            throw RuntimeException(exception)
        }
    }


    @ExperimentalCoroutinesApi
    override suspend fun getCharacterDetail(url: String): CharacterDetailsModel {
        try {
            if (starWarCache.getCharacterDetail(url) == null ) {
                val characterDto = starWarClient.getCharacterDetail(url)
                val characterModel = characterDto.toDomainModel()
                starWarCache.cacheCharacterDetail( Pair(url, characterModel))
                return characterModel
            }
            return starWarCache.getCharacterDetail(url)!!
        } catch (exception: FailedRequest) {
            throw RuntimeException(exception)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getPlanetDetails(url: String): PlanetDetailsModel {
        try {
            val planetDto = starWarClient.getPlanetDetails(url)
            return planetDto.toDomainModel()

        } catch (exception: FailedRequest) {
            throw RuntimeException(exception)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getSpeciesDetails(url: String): SpeciesDetailsModel {
        try {
            val speciesDto = starWarClient.getSpeciesDetails(url)
            return speciesDto.toDomainModel()

        } catch (exception: FailedRequest) {
            throw RuntimeException(exception)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getFilmsDetails(url: String): MoviesDetailsModel {
        try {
            val moviesDto = starWarClient.getFilmsDetails(url)
            return moviesDto.toDomainModel()

        } catch (exception: FailedRequest) {
            throw RuntimeException(exception)
        }
    }
}