package com.example.githubtest.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubtest.domain.model.SearchUser
import com.example.githubtest.domain.repository.SearchUserRepository
import com.example.githubtest.ui.detail.UserDetailFragment.Companion.KEY_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: SearchUserRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel()  {


    val id = savedStateHandle.getLiveData<Int>(KEY_ID).value ?: 0


    fun getItem() : Flow<SearchUser?> = repository.getItem(id)

    fun updateFavorite(id: Int, isFavorite: Boolean) {
        repository.updateFavorite(id, isFavorite)
    }
}