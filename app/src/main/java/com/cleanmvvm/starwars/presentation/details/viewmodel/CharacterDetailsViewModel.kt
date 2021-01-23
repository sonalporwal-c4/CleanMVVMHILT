package com.cleanmvvm.starwars.presentation.details.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.cleanmvvm.starwars.data.base.BaseException
import com.cleanmvvm.starwars.domain.interactors.GetCharacterDetailsUseCase
import com.cleanmvvm.starwars.domain.interactors.GetMoviesDetailsUseCase
import com.cleanmvvm.starwars.domain.interactors.GetPlanetDetailsUseCase
import com.cleanmvvm.starwars.domain.interactors.GetSpicesDetailsUseCase
import com.cleanmvvm.starwars.domain.model.CharacterDetailsModel
import com.cleanmvvm.starwars.domain.model.MoviesDetailsModel
import com.cleanmvvm.starwars.domain.model.PlanetDetailsModel
import com.cleanmvvm.starwars.domain.model.SpeciesDetailsModel
import com.cleanmvvm.starwars.presentation.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


class CharacterDetailsViewModel @ViewModelInject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val getMoviesDetailsUseCase: GetMoviesDetailsUseCase,
    private val getPlanetDetailsUseCase: GetPlanetDetailsUseCase,
    private val getSpicesDetailsUseCase: GetSpicesDetailsUseCase
) : BaseViewModel<CharacterDetailsViewData>() {

    override fun defaultState() = CharacterDetailsViewData()

    @ExperimentalCoroutinesApi
    fun getCharacterDetails(url: String){
        viewModelScope.launch {
            newState { it.copy( characterDetailsViewState= CharacterDetailsViewState.GET_CHARACTER_LOADING) }
            try {
                val characters = getCharacterDetailsUseCase.execute(url)
                newState {
                    it.copy(
                        characterDetailsViewState = CharacterDetailsViewState.SHOW_DETAILS,
                        characterDetailsResponse = characters
                    )
                }
            } catch (exception: BaseException) {
                newState {
                    it.copy(
                        characterDetailsViewState = CharacterDetailsViewState.SHOW_GET_ERROR,
                        errorCode = exception.errorCode.toString()
                    )
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun getMoviesDetails(films: List<String>) {
        films.forEach { film ->
            viewModelScope.launch {
                newState { it.copy( characterDetailsViewState= CharacterDetailsViewState.GET_MOVIES_LOADING) }
                try {
                    val movieDetails = getMoviesDetailsUseCase.execute(film)
                    newState {
                        it.copy(
                            characterDetailsViewState = CharacterDetailsViewState.SHOW_MOVIES,
                            moviesDetailsResponse = movieDetails
                        )
                    }
                } catch (exception: BaseException) {
                    newState {
                        it.copy(
                            characterDetailsViewState = CharacterDetailsViewState.SHOW_GET_ERROR,
                            errorCode = exception.errorCode.toString()
                        )
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun getPlanetDetails(planet: String) {
            viewModelScope.launch {
                viewModelScope.launch {
                    newState { it.copy( characterDetailsViewState= CharacterDetailsViewState.GET_PLANET_LOADING) }
                    try {
                        val planetDetails = getPlanetDetailsUseCase.execute(planet)
                        newState {
                            it.copy(
                                characterDetailsViewState = CharacterDetailsViewState.SHOW_PLANETS,
                                planetDetailsResponse = planetDetails
                            )
                        }
                    } catch (exception: BaseException) {
                        newState {
                            it.copy(
                                characterDetailsViewState = CharacterDetailsViewState.SHOW_GET_ERROR,
                                errorCode = exception.errorCode.toString()
                            )
                        }
                    }
                }
            }
        }

    @ExperimentalCoroutinesApi
    fun getSpeciesDetails(species: List<String>) {
        species.forEach { specie ->
            viewModelScope.launch {
                viewModelScope.launch {
                    newState { it.copy( characterDetailsViewState= CharacterDetailsViewState.GET_SPECIES_LOADING) }
                    try {
                        val specieDetails = getSpicesDetailsUseCase.execute(specie)
                        newState {
                            it.copy(
                                characterDetailsViewState = CharacterDetailsViewState.SHOW_SPECIES,
                                speciesDetailsResponse = specieDetails
                            )
                        }
                    } catch (exception: BaseException) {
                        newState {
                            it.copy(
                                characterDetailsViewState = CharacterDetailsViewState.SHOW_GET_ERROR,
                                errorCode = exception.errorCode.toString()
                            )
                        }
                    }
                }
            }
        }
    }
}

data class CharacterDetailsViewData(
    val characterDetailsViewState: CharacterDetailsViewState = CharacterDetailsViewState.GET_CHARACTER_LOADING,
    val errorCode: String = "",
    val characterDetailsResponse: CharacterDetailsModel? = null,
    val moviesDetailsResponse: MoviesDetailsModel? = null,
    val planetDetailsResponse: PlanetDetailsModel? = null,
    val speciesDetailsResponse: SpeciesDetailsModel? = null
)

enum class CharacterDetailsViewState {
    GET_CHARACTER_LOADING,
    GET_MOVIES_LOADING,
    GET_PLANET_LOADING,
    GET_SPECIES_LOADING,
    SHOW_GET_ERROR,
    SHOW_DETAILS,
    SHOW_MOVIES,
    SHOW_PLANETS,
    SHOW_SPECIES
}
