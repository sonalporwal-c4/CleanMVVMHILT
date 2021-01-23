package com.cleanmvvm.starwars.data.repository

import android.net.Uri
import com.cleanmvvm.starwars.data.base.retry
import com.cleanmvvm.starwars.data.base.returnOrThrow
import com.cleanmvvm.starwars.data.dto.*
import com.cleanmvvm.starwars.data.network.StarWarAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarClientImpl @Inject constructor(private val starWarApi: StarWarAPI) :
    StarWarClient {

    @ExperimentalCoroutinesApi
    override suspend fun searchCharacterByrName(characterName: String): CharacterSearchDTO {
        return returnOrThrow { retry { starWarApi.searchCharacterName(characterName = characterName) } }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getCharacterDetail(url: String): CharacterDetailsDTO {
        return returnOrThrow { retry { starWarApi.getCharacterDetail(url) } }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getPlanetDetails(url: String): PlanetDetailsDTO {
        return returnOrThrow { retry { starWarApi.getPlanetDetails(url) } }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getSpeciesDetails(url: String): SpeciesDetailsDTO {
        return returnOrThrow { retry { starWarApi.getSpeciesDetails(url) } }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getFilmsDetails(url: String): MoviesDetailsDTO {
        return returnOrThrow { retry { starWarApi.getFilmsDetails(url) } }
    }
}