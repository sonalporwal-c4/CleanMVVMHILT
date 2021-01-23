package com.cleanmvvm.starwars.data.repository

import com.cleanmvvm.starwars.data.base.BaseTest
import com.cleanmvvm.starwars.data.base.FailedRequest
import com.cleanmvvm.starwars.data.dto.*
import com.cleanmvvm.starwars.data.network.StarWarAPI
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test

import retrofit2.Response

class StarWarClientImplTest : BaseTest<StarWarClientImpl>() {

    override lateinit var classUnderTest: StarWarClientImpl

    @MockK
    lateinit var starWarApi: StarWarAPI

    @MockK
    lateinit var characterSearchResponse: Response<CharacterSearchDTO>

    @MockK
    lateinit var characterDetailsResponse: Response<CharacterDetailsDTO>

    @MockK
    lateinit var moviesDetailsnResponse: Response<MoviesDetailsDTO>

    @MockK
    lateinit var planetDetailsResponse: Response<PlanetDetailsDTO>

    @MockK
    lateinit var speciesDetailsResponse: Response<SpeciesDetailsDTO>

    @MockK
    lateinit var responseBody: ResponseBody


    private val characterUrl = "http://swapi.dev/api/people/1/"
    private val moviesUrl = "http://swapi.dev/api/films/1/"
    private val planetUrl = "http://swapi.dev/api/planets/1/"
    private val speciesUrl = "http://swapi.dev/api/species/2"

    @Before
    override fun setup() {
        super.setup()
        classUnderTest = StarWarClientImpl(starWarApi)
    }

    /**
     * GIVEN user exists on the API
     * WHEN I invoke the SearchCharacterByName method
     * THEN I expect user object (CharacterSearchDTO) to be returned
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testSearchCharacterByNameSuccess() = runBlocking {
        coEvery { starWarApi.searchCharacterName("Luk") } returns characterSearchResponse
        coEvery { characterSearchResponse.code() } returns 200
        coEvery { characterSearchResponse.body() } returns searchCharacterList()

        val characters = classUnderTest.searchCharacterByrName("Luk")
        assertNotNull(characters)
    }

    /**
     * GIVEN I have an invalid token
     * WHEN I invoke the SearchCharacterByName method
     * THEN I expect InvalidTokenException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testSearchCharacterByNameInvalidToken() = runBlocking {
        coEvery { starWarApi.searchCharacterName("Luk") } returns characterSearchResponse
        coEvery { characterSearchResponse.code() } returns 403
        coEvery { characterSearchResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns "Invalid token"

        try {
            classUnderTest.searchCharacterByrName("Luk")
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN I have not registered with C3LX
     * WHEN I invoke the SearchCharacterByName method
     * THEN I expect NotFoundException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testSearchCharacterByNameNotFound() = runBlocking {
        coEvery { starWarApi.searchCharacterName("Luk") } returns characterSearchResponse
        coEvery { characterSearchResponse.code() } returns 403
        coEvery { characterSearchResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns ""

        try {
            classUnderTest.searchCharacterByrName("Luk")
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN the C3LX servers returns response code that's not a 200/403/5XX
     * WHEN I invoke the SearchCharacterByName method
     * THEN I expect FailedRequest to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testSearchCharacterByNameFailedRequest() = runBlocking {
        coEvery { starWarApi.searchCharacterName("Luk") } returns characterSearchResponse
        coEvery { characterSearchResponse.code() } returns 422
        try {
            classUnderTest.searchCharacterByrName("Luk")
            assertTrue(false)
        } catch (e: FailedRequest) {
            assertEquals(422, e.responseCode)
        }
    }

    /**
     * GIVEN user exists on the API
     * WHEN I invoke the SearchCharacterByName method
     * THEN I expect user object (CharacterDetailsDTO) to be returned
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetCharacterDetailSuccess() = runBlocking {
        coEvery { starWarApi.getCharacterDetail(characterUrl) } returns characterDetailsResponse
        coEvery { characterDetailsResponse.code() } returns 200
        coEvery { characterDetailsResponse.body() } returns characterDetails()

        val characterDetails = classUnderTest.getCharacterDetail(characterUrl)
        assertNotNull(characterDetails)
    }

    /**
     * GIVEN I have an invalid token
     * WHEN I invoke the GetCharacterDetail method
     * THEN I expect InvalidTokenException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetCharacterDetailInvalidToken() = runBlocking {
        coEvery { starWarApi.getCharacterDetail(characterUrl) } returns characterDetailsResponse
        coEvery { characterDetailsResponse.code() } returns 403
        coEvery { characterDetailsResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns "Invalid token"

        try {
            classUnderTest.getCharacterDetail(characterUrl)
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN I have not registered with C3LX
     * WHEN I invoke the GetCharacterDetail method
     * THEN I expect NotFoundException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetCharacterDetailNotFound() = runBlocking {
        coEvery { starWarApi.getCharacterDetail(characterUrl)} returns characterDetailsResponse
        coEvery { characterDetailsResponse.code() } returns 403
        coEvery { characterDetailsResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns ""

        try {
            classUnderTest.getCharacterDetail(characterUrl)
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN the C3LX servers returns response code that's not a 200/403/5XX
     * WHEN I invoke the GetCharacterDetail method
     * THEN I expect FailedRequest to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetCharacterDetailFailedRequest() = runBlocking {
        coEvery { starWarApi.getCharacterDetail(characterUrl) } returns characterDetailsResponse
        coEvery { characterDetailsResponse.code() } returns 422
        try {
            classUnderTest.getCharacterDetail(characterUrl)
            assertTrue(false)
        } catch (e: FailedRequest) {
            assertEquals(422, e.responseCode)
        }
    }


    /**
     * GIVEN user exists on the API
     * WHEN I invoke the GetPlanetDetails method
     * THEN I expect user object (CharacterDetailsDTO) to be returned
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetPlanetDetailsSuccess() = runBlocking {
        coEvery { starWarApi.getPlanetDetails(planetUrl) } returns planetDetailsResponse
        coEvery { planetDetailsResponse.code() } returns 200
        coEvery { planetDetailsResponse.body() } returns planetDetailsList()

        val planetDetails = classUnderTest.getPlanetDetails(planetUrl)
        assertNotNull(planetDetails)
    }

    /**
     * GIVEN I have an invalid token
     * WHEN I invoke the GetPlanetDetails method
     * THEN I expect InvalidTokenException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetPlanetDetailsInvalidToken() = runBlocking {
        coEvery { starWarApi.getPlanetDetails(planetUrl) } returns planetDetailsResponse
        coEvery { planetDetailsResponse.code() } returns 403
        coEvery { planetDetailsResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns "Invalid token"

        try {
            classUnderTest.getPlanetDetails(planetUrl)
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN I have not registered with C3LX
     * WHEN I invoke the GetPlanetDetails method
     * THEN I expect NotFoundException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetPlanetDetailsNotFound() = runBlocking {
        coEvery { starWarApi.getPlanetDetails(planetUrl)} returns planetDetailsResponse
        coEvery { planetDetailsResponse.code() } returns 403
        coEvery { planetDetailsResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns ""

        try {
            classUnderTest.getPlanetDetails(planetUrl)
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN the C3LX servers returns response code that's not a 200/403/5XX
     * WHEN I invoke the GetPlanetDetails method
     * THEN I expect FailedRequest to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetPlanetDetailsFailedRequest() = runBlocking {
        coEvery { starWarApi.getPlanetDetails(planetUrl) } returns planetDetailsResponse
        coEvery { planetDetailsResponse.code() } returns 422
        try {
            classUnderTest.getPlanetDetails(planetUrl)
            assertTrue(false)
        } catch (e: FailedRequest) {
            assertEquals(422, e.responseCode)
        }
    }


    /**
     * GIVEN user exists on the API
     * WHEN I invoke the GetMoviesDetails method
     * THEN I expect user object (CharacterDetailsDTO) to be returned
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetMoviesDetailsSuccess() = runBlocking {
        coEvery { starWarApi.getFilmsDetails(moviesUrl) } returns moviesDetailsnResponse
        coEvery { moviesDetailsnResponse.code() } returns 200
        coEvery { moviesDetailsnResponse.body() } returns moviesDetailsList()

        val moviesDetails = classUnderTest.getFilmsDetails(moviesUrl)
        assertNotNull(moviesDetails)
    }

    /**
     * GIVEN I have an invalid token
     * WHEN I invoke the GetMoviesDetails method
     * THEN I expect InvalidTokenException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetMoviesDetailsInvalidToken() = runBlocking {
        coEvery { starWarApi.getFilmsDetails(moviesUrl) } returns moviesDetailsnResponse
        coEvery { moviesDetailsnResponse.code() } returns 403
        coEvery { moviesDetailsnResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns "Invalid token"

        try {
            classUnderTest.getFilmsDetails(moviesUrl)
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN I have not registered with C3LX
     * WHEN I invoke the GetMoviesDetails method
     * THEN I expect NotFoundException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetMoviesDetailsNotFound() = runBlocking {
        coEvery { starWarApi.getFilmsDetails(moviesUrl)} returns moviesDetailsnResponse
        coEvery { moviesDetailsnResponse.code() } returns 403
        coEvery { moviesDetailsnResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns ""

        try {
            classUnderTest.getFilmsDetails(moviesUrl)
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN the C3LX servers returns response code that's not a 200/403/5XX
     * WHEN I invoke the GetMoviesDetails method
     * THEN I expect FailedRequest to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetMoviesDetailsFailedRequest() = runBlocking {
        coEvery { starWarApi.getFilmsDetails(moviesUrl) } returns moviesDetailsnResponse
        coEvery { moviesDetailsnResponse.code() } returns 422
        try {
            classUnderTest.getFilmsDetails(moviesUrl)
            assertTrue(false)
        } catch (e: FailedRequest) {
            assertEquals(422, e.responseCode)
        }
    }


    /**
     * GIVEN user exists on the API
     * WHEN I invoke the GetSpeciesDetails method
     * THEN I expect user object (CharacterDetailsDTO) to be returned
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetSpeciesDetailsSuccess() = runBlocking {
        coEvery { starWarApi.getSpeciesDetails(speciesUrl) } returns speciesDetailsResponse
        coEvery { speciesDetailsResponse.code() } returns 200
        coEvery { speciesDetailsResponse.body() } returns speciesDetailsList()

        val speciesDetails = classUnderTest.getSpeciesDetails(speciesUrl)
        assertNotNull(speciesDetails)
    }

    /**
     * GIVEN I have an invalid token
     * WHEN I invoke the GetSpeciesDetails method
     * THEN I expect InvalidTokenException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetSpeciesDetailsInvalidToken() = runBlocking {
        coEvery { starWarApi.getSpeciesDetails(speciesUrl) } returns speciesDetailsResponse
        coEvery { speciesDetailsResponse.code() } returns 403
        coEvery { speciesDetailsResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns "Invalid token"

        try {
            classUnderTest.getSpeciesDetails(speciesUrl)
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN I have not registered with C3LX
     * WHEN I invoke the GetSpeciesDetails method
     * THEN I expect NotFoundException to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetSpeciesDetailsNotFound() = runBlocking {
        coEvery { starWarApi.getSpeciesDetails(speciesUrl)} returns speciesDetailsResponse
        coEvery { speciesDetailsResponse.code() } returns 403
        coEvery { speciesDetailsResponse.errorBody() } returns responseBody
        coEvery { responseBody.toString() } returns ""

        try {
            classUnderTest.getSpeciesDetails(speciesUrl)
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    /**
     * GIVEN the C3LX servers returns response code that's not a 200/403/5XX
     * WHEN I invoke the GetSpeciesDetails method
     * THEN I expect FailedRequest to be thrown
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetSpeciesDetailsFailedRequest() = runBlocking {
        coEvery { starWarApi.getSpeciesDetails(speciesUrl)} returns speciesDetailsResponse
        coEvery { speciesDetailsResponse.code() } returns 422
        try {
            classUnderTest.getSpeciesDetails(speciesUrl)
            assertTrue(false)
        } catch (e: FailedRequest) {
            assertEquals(422, e.responseCode)
        }
    }


}

