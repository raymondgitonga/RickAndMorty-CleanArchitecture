package com.tosh.rickandmorty.model

import com.tosh.rickandmorty.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CharactersService {

    @Inject
    lateinit var api: CharactersApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCharacters(): Single<CharacterModel>{
        return api.getCharacters()
    }
}