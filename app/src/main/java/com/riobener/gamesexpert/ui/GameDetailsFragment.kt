package com.riobener.gamesexpert.ui

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.riobener.gamesexpert.databinding.FragmentGameDetailsBinding
import com.riobener.gamesexpert.ui.viewmodels.GameDetailsViewModel
import com.riobener.gamesexpert.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_game_details.*
import kotlinx.android.synthetic.main.fragment_games_list.*
import kotlinx.android.synthetic.main.game_preview_element.view.*


@AndroidEntryPoint
class GameDetailsFragment : Fragment() {
    private var _binding: FragmentGameDetailsBinding? = null
    private val mBinding get() = _binding!!

    private val args: GameDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<GameDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameId = args.gameId
        viewModel.getGameDetails(gameId)
        viewModel.gameLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    detailsProgressBar.visibility = View.INVISIBLE
                    response.data?.let{
                        val imageList = ArrayList<SlideModel>()
                        for(image in it.images.results){
                            imageList.add(SlideModel(image.image,ScaleTypes.CENTER_INSIDE) )
                        }
                        Glide.with(this).load(it.details.background_image).into(game_bg)
                        game_bg.clipToOutline = true
                        game_title.text = it.details.name
                        releasedText.text = it.details.released
                        ratingText.text = it.details.rating.toString()
                        metacriticText.text = it.details.metacritic.toString()
                        game_description.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(it.details.description, Html.FROM_HTML_MODE_COMPACT)
                        } else {
                            Html.fromHtml(it.details.description)
                        }
                        website_link.text = it.details.website
                        metacritic_link.text = it.details.metacritic_url
                        image_slider.setImageList(imageList)
                        image_slider.startSliding(1500)
                    }
                }
                is Resource.Error -> {
                    detailsProgressBar.visibility = View.INVISIBLE
                    response.data?.let{
                        Log.e("wrongData", "GameDetailsFragment: ${it}")
                    }
                }
                is Resource.Loading ->{
                    detailsProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}