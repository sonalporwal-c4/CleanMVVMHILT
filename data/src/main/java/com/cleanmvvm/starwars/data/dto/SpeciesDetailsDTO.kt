package com.cleanmvvm.starwars.data.dto

import com.cleanmvvm.starwars.domain.model.SpeciesDetailsModel


/* Species details of a character */
class SpeciesDetailsDTO(
    val homeworld: String?,
    val language: String?,
    val name: String
)

fun SpeciesDetailsDTO.toDomainModel(): SpeciesDetailsModel {
    return SpeciesDetailsModel(
        homeworld, language, name
    )
}