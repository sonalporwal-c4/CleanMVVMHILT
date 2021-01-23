package com.cleanmvvm.starwars.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cleanmvvm.starwars.databinding.SearchCharacterItemBinding
import com.cleanmvvm.starwars.domain.model.CharacterSearchDetailsModel
import kotlin.properties.Delegates


class HomeSearchAdapter(private val clickListener: (String) -> Unit)
: RecyclerView.Adapter<HomeSearchAdapter.SearchCharactersViewHolder>() {

    var itemList: List<CharacterSearchDetailsModel> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCharactersViewHolder {
        val characterSearchItemBinding = SearchCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchCharactersViewHolder(characterSearchItemBinding)
    }


    override fun onBindViewHolder(holder: SearchCharactersViewHolder, position: Int) {

        val character = itemList[position]
        holder.bind(character)
        holder.itemView.setOnClickListener { clickListener(character.url)  }
    }

    override fun getItemCount() = itemList.size

    class SearchCharactersViewHolder(private val binding: SearchCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterSearchDetailsModel) {
            binding.txtName.text = character.name
            binding.txtBirthDate.text = character.birth_year
        }
    }
}