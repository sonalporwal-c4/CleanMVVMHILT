package com.cleanmvvm.starwars.domain.model

data class CharacterSearchModel(
    val count: Int,
    val results: List<CharacterSearchDetailsModel>
)

data class CharacterSearchDetailsModel(
    val birth_year: String,
    val name: String,
    val url: String
)
