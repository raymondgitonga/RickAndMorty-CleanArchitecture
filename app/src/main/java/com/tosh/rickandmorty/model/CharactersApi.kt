package com.tosh.rickandmorty.model

import io.reactivex.Single
import retrofit2.http.GET

interface CharactersApi {

    @GET("api/character/")
    fun getCharacters(): Single<CharacterModel>
}