package com.tosh.rickandmorty.di

import com.tosh.rickandmorty.model.CharactersService
import com.tosh.rickandmorty.viewmodel.CharacterViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CharactersService)
    fun inject(viewModel: CharacterViewModel)
}