package com.riobener.gamesexpert.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.riobener.gamesexpert.R
import com.riobener.gamesexpert.data.model.Game
import com.riobener.gamesexpert.databinding.FragmentGamesListBinding
import com.riobener.gamesexpert.ui.adapters.GamesAdapter
import com.riobener.gamesexpert.ui.viewmodels.GamesListViewModel
import com.riobener.gamesexpert.ui.viewmodels.LoginViewModel
import com.riobener.gamesexpert.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_games_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesListFragment : Fragment() {
    private var _binding: FragmentGamesListBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<GamesListViewModel>()
    lateinit var gamesAdapter: GamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesListBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
        /*viewModel.gamesLiveData.observe(viewLifecycleOwner){response->
            when (response) {
                is Resource.Success -> {
                    gamesProgressBar.visibility = View.INVISIBLE
                    response.data?.let{
                        gamesAdapter.differ.submitList(it.results)
                    }
                }
                is Resource.Error -> {
                    gamesProgressBar.visibility = View.INVISIBLE
                    response.data?.let{
                        Log.e("wrongData", "GamesFragment: ${it}")
                    }
                }
                is Resource.Loading ->{
                    gamesProgressBar.visibility = View.VISIBLE
                }
            }
        }*/
    }

    private fun initAdapter(view: View) {
        gamesAdapter = GamesAdapter(GamesAdapter.GamesComparator)
        gamesAdapter.onItemClick = {
            val action = GamesListFragmentDirections.actionGamesListFragmentToGameDetailsFragment(it.id)
            Navigation.findNavController(view).navigate(action)
        }
        gamesList.apply {
            adapter = gamesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        lifecycleScope.launch {
            viewModel.games.collectLatest { pagingData ->
                gamesAdapter.submitData(pagingData)
            }
        }

    }
}