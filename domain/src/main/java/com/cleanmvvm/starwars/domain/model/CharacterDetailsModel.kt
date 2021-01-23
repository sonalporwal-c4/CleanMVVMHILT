package com.cleanmvvm.starwars.domain.model


data class CharacterDetailsModel(
   val birth_year: String,
   val films: List<String>,
   val height: String,
   val homeworld: String,
   val name: String,
   val species: List<String>
)
