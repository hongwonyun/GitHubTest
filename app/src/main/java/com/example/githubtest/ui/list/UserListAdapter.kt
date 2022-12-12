package com.example.githubtest.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.githubtest.databinding.ItemUserBinding
import com.example.githubtest.domain.model.SearchUser

class UserListAdapter(
    private val listener: ListOnClickListener
): PagingDataAdapter<SearchUser, SearchUserViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        return SearchUserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item, position, false, listener)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<SearchUser>() {
            override fun areItemsTheSame(oldItem: SearchUser, newItem: SearchUser): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: SearchUser, newItem: SearchUser): Boolean =
                oldItem == newItem

        }
    }
}