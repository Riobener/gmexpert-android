package com.riobener.gamesexpert.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.riobener.gamesexpert.R
import com.riobener.gamesexpert.databinding.FragmentFavoriteBinding
import com.riobener.gamesexpert.databinding.FragmentSearchBinding
import com.riobener.gamesexpert.ui.adapters.FavoritesAdapter
import com.riobener.gamesexpert.ui.adapters.GamesAdapter
import com.riobener.gamesexpert.ui.adapters.SearchGamesAdapter
import com.riobener.gamesexpert.ui.viewmodels.GamesListViewModel
import com.riobener.gamesexpert.ui.viewmodels.SearchViewModel
import com.riobener.gamesexpert.utils.Constants.Companion.TOKEN_QUERY
import com.riobener.gamesexpert.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<GamesListViewModel>()
    lateinit var gamesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    private fun getToken(): String {
        val prefs = this.activity!!.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        return prefs.getString("token", "")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
        viewModel.getFavorites(TOKEN_QUERY+getToken())
        viewModel.favoritesLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    favoriteProgress.visibility = View.INVISIBLE
                    response.data?.let {
                        gamesAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    favoriteProgress.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("checkData", "MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    favoriteProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter(view: View) {
        gamesAdapter = FavoritesAdapter()
        gamesAdapter.onItemClick = {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToGameDetailsFragment(it.gameId.toInt())
            Navigation.findNavController(view).navigate(action)
        }
        favorite_game_list.apply {
            adapter = gamesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}