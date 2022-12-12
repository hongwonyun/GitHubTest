package com.example.githubtest.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubtest.domain.model.SearchUser
import com.example.githubtest.domain.repository.SearchUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: SearchUserRepository
): ViewModel()  {

    val query = MutableStateFlow<String>("android")

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedList: Flow<PagingData<SearchUser>> = query.flatMapLatest { query ->
        repository.fetchItems(query)
    }.cachedIn(viewModelScope)

    fun updateFavorite(id: Int, isFavorite: Boolean) {
        repository.updateFavorite(id, isFavorite)
    }

    val favoriteItems: Flow<List<SearchUser>> = repository.getFavoriteItems()
}