package com.cleanmvvm.starwars.presentation.home.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cleanmvvm.starwars.R
import com.cleanmvvm.starwars.databinding.FragmentHomeSearchBinding
import com.cleanmvvm.starwars.presentation.home.viewmodel.SearchCharacterViewData
import com.cleanmvvm.starwars.presentation.home.viewmodel.SearchCharacterViewModel
import com.cleanmvvm.starwars.presentation.home.viewmodel.SearchCharacterViewState
import com.cleanmvvm.starwars.presentation.home.adapter.HomeSearchAdapter
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class HomeSearchFragment : Fragment(R.layout.fragment_home_search) {

    private val viewModel: SearchCharacterViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeSearchBinding
    private lateinit var adapterHome: HomeSearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeSearchBinding.inflate(layoutInflater, container, false)
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        adapterHome = HomeSearchAdapter{ url: String ->
            onCharacterSelected(url)
        }
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapterHome

        binding.imgSearch.setOnClickListener {
            searchCharacter(binding.edtSearchName.text.toString())
            view.hideKeyboard()
        }

        // starting the search by keyboard
        binding.edtSearchName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchCharacter(binding.edtSearchName.text.toString())
            }
            true
        }

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            setViewData(viewState)
        })
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun setViewData(data: SearchCharacterViewData) {
        when (data.searchCharacterViewState) {
            SearchCharacterViewState.GET_LOADING -> setViewState(load = true)
            SearchCharacterViewState.SHOW_CHARACTERS -> {
                data.characterSearchResult.apply {
                    if(this.isNullOrEmpty()){
                        setViewState(failure = true)
                        binding.errorLayout.root.errorText.text = resources.getString(R.string.no_character_found)
                    }else {
                        setViewState(content = true)
                        data.characterSearchResult?.let { adapterHome.itemList = it }
                    }
                }
            }
            SearchCharacterViewState.SHOW_ERROR -> setViewState(failure = true)
        }
    }


    private fun setViewState(failure: Boolean = false, load: Boolean = false, content: Boolean = false) {
        with(binding) {
            loading.isVisible = load
            errorLayout.root.isVisible = failure
            recycler.isVisible = content
        }
    }


    @ExperimentalCoroutinesApi
    private fun searchCharacter(characterName: String) {
        if(characterName.isNotEmpty()) {
            viewModel.getSearchCharactersByName(characterName)
        }
    }

    private fun onCharacterSelected(url: String) {
        val action = HomeSearchFragmentDirections.showCharacterDetailsAction(url)
        Navigation.findNavController(binding.rootView).navigate(action)
    }

}