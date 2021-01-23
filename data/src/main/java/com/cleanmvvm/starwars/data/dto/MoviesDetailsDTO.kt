package com.cleanmvvm.starwars.data.dto

import com.cleanmvvm.starwars.domain.model.MoviesDetailsModel


/* Response of movie details of a character */
data class MoviesDetailsDTO(
    val opening_crawl: String,
    val release_date: String,
    val title: String
)

fun MoviesDetailsDTO.toDomainModel(): MoviesDetailsModel {
    return MoviesDetailsModel(
        opening_crawl, release_date, title
    )
}
