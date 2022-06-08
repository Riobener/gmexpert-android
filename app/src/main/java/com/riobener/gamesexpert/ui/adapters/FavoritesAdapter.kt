package com.riobener.gamesexpert.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riobener.gamesexpert.R
import com.riobener.gamesexpert.data.model.FavoriteResponse
import kotlinx.android.synthetic.main.game_preview_element.view.*

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val callBack = object : DiffUtil.ItemCallback<FavoriteResponse>() {
        override fun areItemsTheSame(oldItem: FavoriteResponse, newItem: FavoriteResponse): Boolean {
            return oldItem.gameId == newItem.gameId
        }

        override fun areContentsTheSame(oldItem: FavoriteResponse, newItem: FavoriteResponse): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {

        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_preview_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val game = differ.currentList[position]
        val gameByPosition = differ.currentList[position]
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(game)
        }
        holder.itemView.apply{
            Glide.with(this).load(gameByPosition.screenshot).into(game_image)
            game_image.clipToOutline = true
            game_title.text = gameByPosition.gameTitle
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onItemClick: ((FavoriteResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (FavoriteResponse) -> Unit) {
        onItemClick = listener
    }

}