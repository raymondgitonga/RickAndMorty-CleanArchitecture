package com.tosh.rickandmorty.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tosh.rickandmorty.di.DaggerApiComponent
import com.tosh.rickandmorty.model.CharResult
import com.tosh.rickandmorty.model.CharactersService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterViewModel : ViewModel() {


    @Inject
    lateinit var charactersService: CharactersService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val characters = MutableLiveData<List<CharResult>>()
    val charactersLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCharacters()
    }


    private fun fetchCharacters() {
        loading.value = true
        disposable.add(
            charactersService.getCharacters()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        characters.value = it.results
                        charactersLoadError.value = false
                        loading.value = false
                    },
                    {
                        charactersLoadError.value = true
                        loading.value = false
                    }

                ))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}