package com.cleanmvvm.starwars.presentation.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.cleanmvvm.starwars.data.base.BaseException
import com.cleanmvvm.starwars.domain.interactors.GetSearchCharacterByNameUseCase
import com.cleanmvvm.starwars.domain.model.CharacterSearchDetailsModel
import com.cleanmvvm.starwars.presentation.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class SearchCharacterViewModel @ViewModelInject constructor(
        private val getSearchCharacterByNameUseCase: GetSearchCharacterByNameUseCase
) : BaseViewModel<SearchCharacterViewData>() {

    override fun defaultState() = SearchCharacterViewData()

    @ExperimentalCoroutinesApi
    fun getSearchCharactersByName(characterName: String) {
        viewModelScope.launch {
            newState { it.copy(searchCharacterViewState = SearchCharacterViewState.GET_LOADING) }
            try {
                val characters = getSearchCharacterByNameUseCase.execute(characterName)
                newState {
                    it.copy(
                            searchCharacterViewState = SearchCharacterViewState.SHOW_CHARACTERS,
                            characterSearchResult = characters.results
                    )
                }
            } catch (exception: BaseException) {
                newState {
                    it.copy(
                            searchCharacterViewState = SearchCharacterViewState.SHOW_ERROR,
                            errorCode = exception.errorCode
                    )
                }
            }
        }
    }
}

data class SearchCharacterViewData(
    val searchCharacterViewState: SearchCharacterViewState = SearchCharacterViewState.NONE,
    val characterSearchResult: List<CharacterSearchDetailsModel>? = null,
    val errorCode: Int? = null
)

enum class SearchCharacterViewState {
    GET_LOADING,
    SHOW_ERROR,
    SHOW_CHARACTERS,
    NONE,
}
