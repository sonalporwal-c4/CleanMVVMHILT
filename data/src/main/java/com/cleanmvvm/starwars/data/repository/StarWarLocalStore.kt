package com.cleanmvvm.starwars.data.repository

import com.cleanmvvm.starwars.domain.model.*
import com.cleanmvvm.starwars.domain.repository.StarWarCache
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarLocalStore @Inject constructor(): StarWarCache {

    private var searchCharacters: MutableList<Pair<String, CharacterSearchModel>>  = mutableListOf()
    private var charactersDetails: MutableList<Pair<String, CharacterDetailsModel>> = mutableListOf()
    private var planetDetails: MutableList<Pair<String, PlanetDetailsModel>> = mutableListOf()
    private var moviesDetails: MutableList<Pair<String, MoviesDetailsModel>> = mutableListOf()
    private var speciesDetails: MutableList<Pair<String, SpeciesDetailsModel>> = mutableListOf()

    @ExperimentalCoroutinesApi
    override suspend fun cacheCharacterByName(data: Pair<String, CharacterSearchModel>) {
       searchCharacters.add(data)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getCharacterByName(characterName: String): CharacterSearchModel? {
        var characterSearchModel: CharacterSearchModel? = null
        for(searchCharacter in searchCharacters!!){
            if(searchCharacter.first == characterName){
                characterSearchModel = searchCharacter.second
            }
        }
        return characterSearchModel
    }

    @ExperimentalCoroutinesApi
    override suspend fun cacheCharacterDetail(detailsData: Pair<String, CharacterDetailsModel>) {
       charactersDetails.add(detailsData)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getCharacterDetail(url: String): CharacterDetailsModel? {
        var characterDetailModel: CharacterDetailsModel? = null
        for(character in charactersDetails!!){
            if(character.first == url){
                characterDetailModel = character.second
            }
        }
        return characterDetailModel
    }

    @ExperimentalCoroutinesApi
    override suspend fun cachePlanetDetail(detailsData: Pair<String, PlanetDetailsModel>) {
        planetDetails.add(detailsData)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getPlanetDetail(url: String): PlanetDetailsModel? {
        var planetDetailModel: PlanetDetailsModel? = null
        for(planet in planetDetails!!){
            if(planet.first == url){
                planetDetailModel = planet.second
            }
        }
        return planetDetailModel
    }

    @ExperimentalCoroutinesApi
    override suspend fun cacheMoviesDetail(detailsData: Pair<String, MoviesDetailsModel>) {
        moviesDetails.add(detailsData)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getMoviesDetail(url: String): MoviesDetailsModel? {
        var moviesDetailModel: MoviesDetailsModel? = null
        for(movies in moviesDetails){
            if(movies.first == url){
                moviesDetailModel = movies.second
            }
        }
        return moviesDetailModel
    }

    @ExperimentalCoroutinesApi
    override suspend fun cacheSpeciesDetail(detailsData: Pair<String, SpeciesDetailsModel>) {
        speciesDetails.add(detailsData)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getSpeciesDetail(url: String): SpeciesDetailsModel? {
        var speciesDetailModel: SpeciesDetailsModel? = null
        for(species in speciesDetails!!){
            if(species.first == url){
                speciesDetailModel = species.second
            }
        }
        return speciesDetailModel
    }

}