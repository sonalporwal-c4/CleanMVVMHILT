package com.cleanmvvm.starwars.viewmodel

import com.cleanmvvm.starwars.data.dto.*

fun searchCharacterList(): CharacterSearchDTO {
    return CharacterSearchDTO(
        count = 5,
        results = listOf(
            CharacterSearchResultDTO(
                name = "Luke Skywalker",
                birth_year = "19BBY",
                url = "http://swapi.dev/api/people/1/"
            )
        )
    )
}

fun characterDetails(): CharacterDetailsDTO {
    return CharacterDetailsDTO(
        name = "Luke Skywalker",
        height =  "172",
        birth_year =  "19BBY",
        homeworld =  "http://swapi.dev/api/planets/1/",
        species = listOf("http://swapi.dev/api/species/2/"),
        films =  listOf(
            "http://swapi.dev/api/films/1/",
            "http://swapi.dev/api/films/2/",
            "http://swapi.dev/api/films/3/",
            "http://swapi.dev/api/films/6/"
        )
    )
}

fun moviesDetailsList(): MoviesDetailsDTO {
    return MoviesDetailsDTO(
        title =  "A New Hope",
        opening_crawl = "It is a period of civil war",
        release_date = "1977-05-25",
    )
}

fun planetDetailsList(): PlanetDetailsDTO {
    return PlanetDetailsDTO(
        name = "Tatooine",
        population =  "200000",
    )
}

fun speciesDetailsList(): SpeciesDetailsDTO {
    return SpeciesDetailsDTO(
        name =  "Human",
        homeworld = "http://swapi.dev/api/planets/9/",
        language =  "Galactic Basic"
    )
}
