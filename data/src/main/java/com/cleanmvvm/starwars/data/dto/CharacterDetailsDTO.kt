package com.cleanmvvm.starwars.data.dto

import com.cleanmvvm.starwars.domain.model.CharacterDetailsModel


/* Response of Character Details */
data class CharacterDetailsDTO(
    val birth_year: String,
    val films: List<String>,
    val height: String,
    val homeworld: String,
    val name: String,
    val species: List<String>
)

fun CharacterDetailsDTO.toDomainModel(): CharacterDetailsModel {
    return CharacterDetailsModel(
            birth_year, films, height, homeworld, name, species
    )
}