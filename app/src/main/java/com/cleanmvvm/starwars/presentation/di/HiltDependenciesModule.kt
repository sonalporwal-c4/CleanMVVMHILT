package com.cleanmvvm.starwars.presentation.di

import com.cleanmvvm.starwars.data.repository.StarWarClient
import com.cleanmvvm.starwars.data.repository.StarWarLocalStore
import com.cleanmvvm.starwars.data.repository.StarWarRepositoryImpl
import com.cleanmvvm.starwars.domain.interactors.*
import com.cleanmvvm.starwars.domain.repository.StarWarCache
import com.cleanmvvm.starwars.domain.repository.StarWarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object HiltDependenciesModule {

    /**
     * Returns a [StarWarCache] impl
     * @param webService [StarWarClient] instance
     */
    @Provides
    fun providesNetworkDataSource(): StarWarCache =
        StarWarLocalStore()


    /**
     * Returns a singleton [StarWarRepository] implementation
     * @param cache [StarWarCache] implementation
     */
    @Provides
    fun provideRepository(cache: StarWarCache, client: StarWarClient): StarWarRepository =
        StarWarRepositoryImpl(cache, client)

    /**
     * Returns a [GetSearchCharacterByNameUseCase] instance
     * @param repository [StarWarRepository] impl
     */
    @Provides
    fun provideSearchCharacterUseCase(repository: StarWarRepository): GetSearchCharacterByNameUseCase =
        GetSearchCharacterByNameUseCase(
            repository
        )

    /**
     * Returns a [GetCharacterDetailsUseCase] instance
     * @param repository [StarWarRepository] impl
     */
    @Provides
    fun provideCharacterDetailsUseCase(repository: StarWarRepository): GetCharacterDetailsUseCase =
        GetCharacterDetailsUseCase(
            repository
        )

    /**
     * Returns a [GetPlanetDetailsUseCase] instance
     * @param repository [StarWarRepository] impl
     */
    @Provides
    fun providePlanetDetailsUseCase(repository: StarWarRepository): GetPlanetDetailsUseCase =
        GetPlanetDetailsUseCase(
            repository
        )

    /**
     * Returns a [GetMoviesDetailsUseCase] instance
     * @param repository [StarWarRepository] impl
     */
    @Provides
    fun provideMoviesDetailsUseCase(repository: StarWarRepository): GetMoviesDetailsUseCase =
        GetMoviesDetailsUseCase(
            repository
        )

    /**
     * Returns a [GetSpicesDetailsUseCase] instance
     * @param repository [StarWarRepository] impl
     */
    @Provides
    fun provideSpeciesDetailsUseCase(repository: StarWarRepository): GetSpicesDetailsUseCase =
        GetSpicesDetailsUseCase(
            repository
        )
}