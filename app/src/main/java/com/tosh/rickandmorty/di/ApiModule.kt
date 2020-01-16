package com.tosh.rickandmorty.di

import com.tosh.rickandmorty.model.CharactersApi
import com.tosh.rickandmorty.model.CharactersService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://rickandmortyapi.com/"


    @Provides
    fun providesCharactersApi(): CharactersApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpInterceptor())
            .build()
            .create(CharactersApi::class.java)
    }

    @Provides
    fun provideCharactersService(): CharactersService {
        return CharactersService()
    }

    private fun httpInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }


}