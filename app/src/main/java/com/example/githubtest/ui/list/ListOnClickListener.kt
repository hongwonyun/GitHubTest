package com.example.githubtest.ui.list

interface ListOnClickListener {
    fun onClickItem(id: Int)
    fun onClickFavorite(id: Int, isFavorite: Boolean)
}