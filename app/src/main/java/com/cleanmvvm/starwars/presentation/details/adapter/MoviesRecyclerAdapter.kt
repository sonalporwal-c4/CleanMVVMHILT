package com.cleanmvvm.starwars.presentation.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cleanmvvm.starwars.R
import com.cleanmvvm.starwars.databinding.MoviesItemBinding
import com.cleanmvvm.starwars.domain.model.MoviesDetailsModel

class MoviesRecyclerAdapter(private val list: List<MoviesDetailsModel>) :
    RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(MoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class MoviesViewHolder(private val binding: MoviesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(speciesDetailsResponse: MoviesDetailsModel) {
            binding.txtTitle.text =
                binding.root.context.getString(R.string.movie_title_is, speciesDetailsResponse.title)
            binding.txtReleaseDate.text = binding.root.context.getString(
                R.string.movie_release_date_is,
                speciesDetailsResponse.release_date
            )
            binding.txtOpening.text = binding.root.context.getString(
                R.string.movie_opening_is,
                speciesDetailsResponse.opening_crawl
            )
        }
    }
}
