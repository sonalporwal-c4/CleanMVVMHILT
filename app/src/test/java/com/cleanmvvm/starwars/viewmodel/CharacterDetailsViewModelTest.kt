package com.cleanmvvm.starwars.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cleanmvvm.starwars.data.base.BaseException
import com.cleanmvvm.starwars.data.dto.toDomainModel
import com.cleanmvvm.starwars.domain.interactors.*
import com.cleanmvvm.starwars.mock
import com.cleanmvvm.starwars.presentation.base.State
import com.cleanmvvm.starwars.presentation.details.viewmodel.CharacterDetailsViewData
import com.cleanmvvm.starwars.presentation.details.viewmodel.CharacterDetailsViewModel
import com.cleanmvvm.starwars.rule.TestCoroutineRule
import com.cleanmvvm.starwars.whenever
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class CharacterDetailsViewModelTest {

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var mockException: BaseException

    private val getCharacterDetailsUseCase = mock<GetCharacterDetailsUseCase>()
    private val getMoviesDetailsUseCase = mock<GetMoviesDetailsUseCase>()
    private val getPlanetDetailsUseCase = mock<GetPlanetDetailsUseCase>()
    private val getSpicesDetailsUseCase = mock<GetSpicesDetailsUseCase>()

    private val observerState = mock<Observer<CharacterDetailsViewData>>()

    private val characterUrl = "http://swapi.dev/api/people/1/"
    private val movieUrl = "http://swapi.dev/api/films/1/"
    private val planetUrl = "http://swapi.dev/api/species/2/"
    private val speciesUrl = "http://swapi.dev/api/planets/1/"

    private val viewModel: CharacterDetailsViewModel by lazy {
        CharacterDetailsViewModel(getCharacterDetailsUseCase, getMoviesDetailsUseCase, getPlanetDetailsUseCase, getSpicesDetailsUseCase)
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { mockException.message } returns "Test Exception"
        viewModel.viewState.observeForever(observerState)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getCharacterDetailsTest() = runBlocking {
        val response = characterDetails().toDomainModel()
        whenever(getCharacterDetailsUseCase.execute(characterUrl))
                .thenReturn(response)

        val argumentCaptor = ArgumentCaptor.forClass(CharacterDetailsViewData::class.java)
        val expectedLoadingState = State.Success
        val expectedDefaultState = State.Loading
        argumentCaptor.run {
            verify(observerState, times(1)).onChanged(capture())
            val (loadingState, defaultState) = Pair(State.Success, State.Loading)
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(defaultState, expectedDefaultState)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getPlanetDetailsTest() = runBlocking {
        val response = planetDetailsList().toDomainModel()
        whenever(getPlanetDetailsUseCase.execute(planetUrl))
            .thenReturn(response)

        val argumentCaptor = ArgumentCaptor.forClass(CharacterDetailsViewData::class.java)
        val expectedLoadingState = State.Success
        val expectedDefaultState = State.Loading
        argumentCaptor.run {
            verify(observerState, times(1)).onChanged(capture())
            val (loadingState, defaultState) = Pair(State.Success, State.Loading)
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(defaultState, expectedDefaultState)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getMoviesDetailsTest() = runBlocking {
        val response = moviesDetailsList().toDomainModel()
        whenever(getMoviesDetailsUseCase.execute(movieUrl))
            .thenReturn(response)

        val argumentCaptor = ArgumentCaptor.forClass(CharacterDetailsViewData::class.java)
        val expectedLoadingState = State.Success
        val expectedDefaultState = State.Loading
        argumentCaptor.run {
            verify(observerState, times(1)).onChanged(capture())
            val (loadingState, defaultState) = Pair(State.Success, State.Loading)
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(defaultState, expectedDefaultState)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getSpeciesDetailsTest() = runBlocking {
        val response = speciesDetailsList().toDomainModel()
        whenever(getSpicesDetailsUseCase.execute(speciesUrl))
            .thenReturn(response)

        val argumentCaptor = ArgumentCaptor.forClass(CharacterDetailsViewData::class.java)
        val expectedLoadingState = State.Success
        val expectedDefaultState = State.Loading
        argumentCaptor.run {
            verify(observerState, times(1)).onChanged(capture())
            val (loadingState, defaultState) = Pair(State.Success, State.Loading)
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(defaultState, expectedDefaultState)
        }
    }
}