package com.cleanmvvm.starwars.data.dto

import com.cleanmvvm.starwars.domain.model.PlanetDetailsModel


/* Response of planet details of a character */
data class PlanetDetailsDTO(
    val name: String,
    val population: String
)

fun PlanetDetailsDTO.toDomainModel(): PlanetDetailsModel {
    return PlanetDetailsModel(
        name, population
    )
}