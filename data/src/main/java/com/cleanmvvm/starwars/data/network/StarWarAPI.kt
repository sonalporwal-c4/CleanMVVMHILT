package com.cleanmvvm.starwars.data.network

import com.cleanmvvm.starwars.data.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Singleton

@Singleton
interface StarWarAPI {

    @GET("https://swapi.dev/api/people/?")
    suspend fun searchCharacterName(@Query("search") characterName: String): Response<CharacterSearchDTO>

    @GET
    suspend fun getCharacterDetail(@Url url: String): Response<CharacterDetailsDTO>

    @GET
    suspend fun getPlanetDetails(@Url url: String): Response<PlanetDetailsDTO>

    @GET
    suspend fun getSpeciesDetails(@Url url: String): Response<SpeciesDetailsDTO>

    @GET
    suspend fun getFilmsDetails(@Url url: String): Response<MoviesDetailsDTO>

}