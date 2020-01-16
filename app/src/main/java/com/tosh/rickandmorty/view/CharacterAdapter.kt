package com.tosh.rickandmorty.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tosh.rickandmorty.R
import com.tosh.rickandmorty.model.CharResult
import com.tosh.rickandmorty.util.getProgressDrawable
import com.tosh.rickandmorty.util.loadImage
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterAdapter(var characters: ArrayList<CharResult>): RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    fun updateCharacters(newCharacters: List<CharResult>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CharacterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
    )

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    class CharacterViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val characterImg = view.characterImg
//        private val characterName = view.characterName
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(character:CharResult){
//            characterName.text = character.name
            characterImg.loadImage(character.image, progressDrawable )
        }
    }
}