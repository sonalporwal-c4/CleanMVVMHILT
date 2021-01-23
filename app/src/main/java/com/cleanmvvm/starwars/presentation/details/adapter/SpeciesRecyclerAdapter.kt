package com.cleanmvvm.starwars.presentation.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cleanmvvm.starwars.databinding.SpeciesItemBinding
import com.cleanmvvm.starwars.domain.model.SpeciesDetailsModel

class SpeciesRecyclerAdapter(private val list: List<SpeciesDetailsModel>) :
    RecyclerView.Adapter<SpeciesRecyclerAdapter.SpeciesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeciesViewHolder =
        SpeciesViewHolder(SpeciesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class SpeciesViewHolder(private val binding: SpeciesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(speciesDetailsResponse: SpeciesDetailsModel) {
            binding.txtName.text = speciesDetailsResponse.name
            binding.txtLanguage.text = speciesDetailsResponse.language
        }
    }
}
