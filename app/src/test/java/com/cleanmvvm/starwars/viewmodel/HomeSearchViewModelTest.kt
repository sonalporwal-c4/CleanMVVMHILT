package com.cleanmvvm.starwars.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cleanmvvm.starwars.data.base.BaseException
import com.cleanmvvm.starwars.data.dto.toDomainModel
import com.cleanmvvm.starwars.domain.interactors.GetSearchCharacterByNameUseCase
import com.cleanmvvm.starwars.mock
import com.cleanmvvm.starwars.presentation.base.State
import com.cleanmvvm.starwars.presentation.home.viewmodel.SearchCharacterViewData
import com.cleanmvvm.starwars.presentation.home.viewmodel.SearchCharacterViewModel
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
class HomeSearchViewModelTest {

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var mockException: BaseException

    @RelaxedMockK
    private lateinit var mockUseCase: GetSearchCharacterByNameUseCase

    private val getSearchCharacterByNameUseCase = mock<GetSearchCharacterByNameUseCase>()
    private val observerState = mock<Observer<SearchCharacterViewData>>()

    private val character = "Luk"

    private val viewModel: SearchCharacterViewModel by lazy {
        SearchCharacterViewModel(mockUseCase)
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { mockException.message } returns "Test Exception"
        viewModel.viewState.observeForever(observerState)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getSearchCharactersByNameTest() = runBlocking {
        val response = searchCharacterList().toDomainModel()
        whenever(getSearchCharacterByNameUseCase.execute(character))
            .thenReturn(response)

        val argumentCaptor = ArgumentCaptor.forClass(SearchCharacterViewData::class.java)
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