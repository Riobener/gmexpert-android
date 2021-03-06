package com.riobener.gamesexpert.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.riobener.gamesexpert.utils.Constants.Companion.TOKEN_QUERY
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

    private fun getToken(): String {
        val prefs = this.activity!!.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        return prefs.getString("token", "")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameId = args.gameId
        viewModel.getGameDetails(gameId, TOKEN_QUERY + getToken())
        viewModel.gameLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    detailsProgressBar.visibility = View.INVISIBLE
                    response.data?.let { gameDetail ->
                        val imageList = ArrayList<SlideModel>()
                        for (image in gameDetail.images.results) {
                            imageList.add(SlideModel(image.image, ScaleTypes.CENTER_INSIDE))
                        }
                        Glide.with(this).load(gameDetail.details.background_image).into(game_bg)
                        game_bg.clipToOutline = true
                        game_title.text = gameDetail.details.name
                        releasedText.text = gameDetail.details.released
                        ratingText.text = gameDetail.details.rating.toString()
                        metacriticText.text = gameDetail.details.metacritic.toString()
                        if (gameDetail.inFavorite) {
                            favoriteStatus.text = "??????????????"
                        } else {
                            favoriteStatus.text = "????????????????"
                        }
                        favoriteStatus.setOnClickListener {
                            if (favoriteStatus.text == "????????????????") {
                                viewModel.addGameToFavorite(
                                    gameId = gameDetail.details.id.toString(),
                                    title = gameDetail.details.name,
                                    screenshot = gameDetail.details.background_image,
                                    token = TOKEN_QUERY + getToken()
                                )
                                favoriteStatus.text = "??????????????"
                            } else {
                                viewModel.deleteFavorite(gameDetail.details.id.toString(),
                                    TOKEN_QUERY + getToken())
                                favoriteStatus.text = "????????????????"
                            }
                        }
                        game_description.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(gameDetail.details.description, Html.FROM_HTML_MODE_COMPACT)
                        } else {
                            Html.fromHtml(gameDetail.details.description)
                        }
                        website_link.text = gameDetail.details.website
                        metacritic_link.text = gameDetail.details.metacritic_url
                        website_link.setOnClickListener {
                            val openURL = Intent(Intent.ACTION_VIEW)
                            if (!website_link.text.toString().isNullOrEmpty()) {
                                openURL.data = Uri.parse(website_link.text.toString())
                                startActivity(openURL)
                            }
                        }
                        metacritic_link.setOnClickListener {
                            val openURL = Intent(Intent.ACTION_VIEW)
                            if (!metacritic_link.text.toString().isNullOrEmpty()) {
                                openURL.data = Uri.parse(metacritic_link.text.toString())
                                startActivity(openURL)
                            }
                        }
                        image_slider.setImageList(imageList)
                        image_slider.startSliding(1500)
                    }
                }
                is Resource.Error -> {
                    detailsProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("wrongData", "GameDetailsFragment: ${it}")
                    }
                }
                is Resource.Loading -> {
                    detailsProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}