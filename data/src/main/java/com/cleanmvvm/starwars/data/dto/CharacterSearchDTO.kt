package com.cleanmvvm.starwars.data.dto

import com.cleanmvvm.starwars.domain.model.CharacterSearchDetailsModel
import com.cleanmvvm.starwars.domain.model.CharacterSearchModel


/* Response of character search by name */
data class CharacterSearchDTO(
    val count: Int,
    val results: List<CharacterSearchResultDTO>
)

/* Details of a character */
data class CharacterSearchResultDTO(
    val birth_year: String,
    val name: String,
    val url: String
)

fun CharacterSearchDTO.toDomainModel(): CharacterSearchModel {
    return CharacterSearchModel(
        count = count,
        results = convertToDetailsList(results)
    )
}

private fun convertToDetailsList(answers: List<CharacterSearchResultDTO>): List<CharacterSearchDetailsModel> {
    var characterDetailsList = mutableListOf<CharacterSearchDetailsModel>()
    answers.forEach {
        characterDetailsList.add(
            CharacterSearchDetailsModel(
                    birth_year = it.birth_year,
                    name = it.name,
                    url = it.url
            )
        )
    }
    return characterDetailsList
}