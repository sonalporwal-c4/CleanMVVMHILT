package com.cleanmvvm.starwars.domain.interactors

import com.cleanmvvm.starwars.domain.repository.StarWarRepository
import com.cleanmvvm.starwars.domain.base.IUseCase
import com.cleanmvvm.starwars.domain.model.MoviesDetailsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviesDetailsUseCase @Inject constructor(private val repository: StarWarRepository)
: IUseCase<String, MoviesDetailsModel> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: String): MoviesDetailsModel {
        return repository.getFilmsDetails(input)
    }

}