package com.riobener.gamesexpert.ui

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
import com.riobener.gamesexpert.databinding.FragmentSearchBinding
import com.riobener.gamesexpert.ui.adapters.GamesAdapter
import com.riobener.gamesexpert.ui.adapters.SearchGamesAdapter
import com.riobener.gamesexpert.ui.viewmodels.SearchViewModel
import com.riobener.gamesexpert.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()
    lateinit var gamesAdapter: SearchGamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter(view)
        var job: Job? = null
        edit_search.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                 delay(1000L)
                text?.let{
                    if(it.toString().isNotEmpty()){
                        viewModel.getSearchGames(query = it.toString())
                    }
                }
            }
        }
        viewModel.searchGamesLiveData.observe(viewLifecycleOwner) { responce ->
            when(responce) {
                is Resource.Success -> {
                    searchProgress.visibility = View.INVISIBLE
                    responce.data?.let {
                        gamesAdapter.differ.submitList(it.results)
                    }
                }
                is Resource.Error -> {
                    searchProgress.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.e("checkData", "MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    searchProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter(view: View) {
        gamesAdapter = SearchGamesAdapter()
        gamesAdapter.onItemClick = {
            val action = SearchFragmentDirections.actionSearchFragmentToGameDetailsFragment(it.id)
            Navigation.findNavController(view).navigate(action)
        }
        search_games_list.apply {
            adapter = gamesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}