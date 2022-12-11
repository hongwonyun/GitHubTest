package com.example.githubtest.ui.list

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtest.databinding.ItemUserBinding
import com.example.githubtest.domain.model.SearchUser

class SearchUserViewHolder(
    private val binding: ItemUserBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchUser, listener: ListOnClickListener) {
        Log.d("TEST", "item : $item")
        binding.item = item
        binding.root.setOnClickListener {
            item.id?.let {
                listener.onClickItem(it)
            }
        }

        binding.btnFavorite.setOnClickListener {
            item.id?.let {
                listener.onClickFavorite(it, item.isFavorite)
            }
        }
    }
}