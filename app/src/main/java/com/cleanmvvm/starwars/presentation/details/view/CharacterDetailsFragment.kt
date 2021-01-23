package com.cleanmvvm.starwars.presentation.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cleanmvvm.starwars.R
import com.cleanmvvm.starwars.databinding.FragmentCharacterDetailsBinding
import com.cleanmvvm.starwars.domain.model.CharacterDetailsModel
import com.cleanmvvm.starwars.domain.model.MoviesDetailsModel
import com.cleanmvvm.starwars.domain.model.PlanetDetailsModel
import com.cleanmvvm.starwars.domain.model.SpeciesDetailsModel
import com.cleanmvvm.starwars.presentation.details.viewmodel.CharacterDetailsViewData
import com.cleanmvvm.starwars.presentation.details.viewmodel.CharacterDetailsViewModel
import com.cleanmvvm.starwars.presentation.details.viewmodel.CharacterDetailsViewState
import com.cleanmvvm.starwars.presentation.details.adapter.MoviesRecyclerAdapter
import com.cleanmvvm.starwars.presentation.details.adapter.SpeciesRecyclerAdapter
import kotlin.math.roundToInt

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private val viewModel: CharacterDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentCharacterDetailsBinding
    private val speciesList = ArrayList<SpeciesDetailsModel>()
    private lateinit var speciesAdapter: SpeciesRecyclerAdapter
    private val filmsList = ArrayList<MoviesDetailsModel>()
    private lateinit var filmsAdapter: MoviesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCharacterDetailsBinding.inflate(layoutInflater, container, false)
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //getting the url to details data or throw an exception
        val url = arguments?.let {
            CharacterDetailsFragmentArgs.fromBundle(it).url
        } ?: throw IllegalArgumentException("Url must nor be null")

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            setViewData(viewState)
        })
        setUpSpeciesRecycler()
        setUpFilmsRecycler()
        viewModel.getCharacterDetails(url)
    }

    private fun setViewData(data: CharacterDetailsViewData) {
        when (data.characterDetailsViewState) {
            CharacterDetailsViewState.GET_CHARACTER_LOADING -> binding.progressDetails.visibility = View.VISIBLE
            CharacterDetailsViewState.GET_MOVIES_LOADING -> binding.progressMovie.visibility = View.VISIBLE
            CharacterDetailsViewState.GET_SPECIES_LOADING -> binding.progressDetails.visibility = View.VISIBLE
            CharacterDetailsViewState.GET_PLANET_LOADING -> binding.progressDetails.visibility = View.VISIBLE
            CharacterDetailsViewState.SHOW_DETAILS -> data.characterDetailsResponse?.let {
                showCharacterDetails(it)
            }
            CharacterDetailsViewState.SHOW_PLANETS -> data.planetDetailsResponse?.let {
                showPlanetDetails(it)
            }
            CharacterDetailsViewState.SHOW_MOVIES -> data.moviesDetailsResponse?.let {
                showMovieDetails(it)
            }
            CharacterDetailsViewState.SHOW_SPECIES -> data.speciesDetailsResponse?.let {
                showSpecieDetails(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (context as AppCompatActivity).onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMovieDetails(moviesDetails: MoviesDetailsModel) {
        binding.progressMovie.visibility = View.GONE
        filmsList.add(moviesDetails)
        filmsAdapter.notifyItemInserted(filmsList.lastIndex)
    }

    private fun showSpecieDetails(speciesDetails: SpeciesDetailsModel) {
        binding.progressDetails.visibility = View.GONE
        speciesList.add(speciesDetails)
        speciesAdapter.notifyItemInserted(speciesList.lastIndex)
    }

    private fun showPlanetDetails(planetDetails: PlanetDetailsModel) {
        binding.progressDetails.visibility = View.GONE
        binding.txtPlanet.text = getString(R.string.character_planet_name_is, planetDetails.name)
        binding.txtPopulation.text = getString(R.string.planet_population_is, planetDetails.population)
    }

    private fun showCharacterDetails(characterDetails: CharacterDetailsModel) {
        binding.txtName.text = characterDetails.name
        binding.txtBirthDate.text = characterDetails.birth_year
        if (characterDetails.height.isDigitsOnly())
            binding.txtHeight.text = getString(
                R.string.character_height_is,
                characterDetails.height,
                getFeet(characterDetails.height),
                getInch(characterDetails.height)
            )
        getFetchOtherData(characterDetails)
    }

    private fun getFetchOtherData(characters: CharacterDetailsModel){
        viewModel.getMoviesDetails(characters.films)
        viewModel.getPlanetDetails(characters.homeworld)
        viewModel.getSpeciesDetails(characters.species)
    }

    private fun setUpFilmsRecycler() {
        filmsAdapter = MoviesRecyclerAdapter(filmsList)
        binding.recyclerFilms.adapter = filmsAdapter
    }

    private fun setUpSpeciesRecycler() {
        speciesAdapter = SpeciesRecyclerAdapter(speciesList)
        binding.recyclerSpecies.adapter = speciesAdapter
    }

    private fun getInch(height: String): String {
        val inch = height.toFloat() / 2.54
        return inch.roundToInt().toString()
    }

    private fun getFeet(height: String): String {
        val feet = height.toFloat() / 30.48
        return feet.roundToInt().toString()
    }
}
