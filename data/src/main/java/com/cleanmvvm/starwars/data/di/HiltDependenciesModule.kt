package com.cleanmvvm.starwars.data.di

import com.cleanmvvm.starwars.data.BuildConfig
import com.cleanmvvm.starwars.data.network.StarWarAPI
import com.cleanmvvm.starwars.data.repository.StarWarClient
import com.cleanmvvm.starwars.data.repository.StarWarClientImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ActivityComponent::class)
@Module
object HiltDependenciesModule {

    /**
     * Returns the [HttpLoggingInterceptor] instance with logging level set to body
     */
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Provides an [OkHttpClient]
     * @param loggingInterceptor [HttpLoggingInterceptor] instance
     */
    @Provides
    fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient().apply {
        OkHttpClient.Builder().run {
            addInterceptor(loggingInterceptor)
            build()
        }
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    /**
     * Returns a [MoshiConverterFactory] instance
     */
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    /**
     * Returns an instance of the [StarWarAPI] interface for the retrofit class
     * @return [StarWarAPI] impl
     */
    @Provides
    fun provideRetrofitInstance(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): StarWarAPI =
        Retrofit.Builder().run {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(gsonConverterFactory)
            client(client)
            build()
        }.run {
            create(StarWarAPI::class.java)
        }


    /**
     * Returns a [StarWarClient] impl
     * @param retrofitClient [StarWarAPI] retrofit interface
     */
    @Provides
    fun providesRetrofitService(retrofitClient: StarWarAPI): StarWarClient =
        StarWarClientImpl(retrofitClient)

}