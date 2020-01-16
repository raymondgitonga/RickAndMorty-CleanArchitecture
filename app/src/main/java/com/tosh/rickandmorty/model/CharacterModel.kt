package com.tosh.rickandmorty.model

data class CharacterModel(
    val results: List<CharResult>
)

data class CharResult(
    val gender: String,
    val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String
)


