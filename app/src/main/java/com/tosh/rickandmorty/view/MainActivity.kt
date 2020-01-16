package com.tosh.rickandmorty.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.tosh.rickandmorty.R
import com.tosh.rickandmorty.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CharacterViewModel
    private val characterAdapter = CharacterAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayoutManager = GridLayoutManager(this@MainActivity, 2)
        charactersList.layoutManager = gridLayoutManager


        viewModel = ViewModelProviders.of(this).get(CharacterViewModel::class.java)
        viewModel.refresh()

        charactersList.apply {
            adapter = characterAdapter
            charactersList.adapter = adapter
        }

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.characters.observe(this, Observer { characters ->
            characters?.let{
                charactersList.visibility = View.VISIBLE
                characterAdapter.updateCharacters(it)
            }
        })

        viewModel.charactersLoadError.observe(this, Observer { isError ->
            isError?.let { errorText.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let{ loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    errorText.visibility = View.GONE
                    charactersList.visibility = View.GONE
                }
            }

        })
    }
}
