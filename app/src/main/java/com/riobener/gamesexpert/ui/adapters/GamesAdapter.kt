package com.riobener.gamesexpert.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riobener.gamesexpert.R
import com.riobener.gamesexpert.data.model.Game
import kotlinx.android.synthetic.main.game_preview_element.view.*

class GamesAdapter : RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    inner class GamesViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val callBack = object : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {

        return GamesViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.game_preview_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val game = differ.currentList[position]
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(game)
        }
        holder.itemView.apply{
            Glide.with(this).load(game.background_image).into(game_image)
            game_image.clipToOutline = true
            game_title.text = game.name
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onItemClick: ((Game) -> Unit)? = null

    fun setOnItemClickListener(listener: (Game) -> Unit) {
        onItemClick = listener
    }

}