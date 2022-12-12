package com.example.githubtest.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.example.githubtest.R
import com.example.githubtest.databinding.FragmentUserDetailBinding
import com.example.githubtest.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment(), ListOnClickListener {

    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding get() = _binding!!
    private val viewModel: UserListViewModel by viewModels { defaultViewModelProviderFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UserListAdapter(this)

        val favoriteListAdapter = UserFavoriteListAdapter(this)
        lifecycleScope.launchWhenCreated {
            viewModel.favoriteItems.collectLatest {
                favoriteListAdapter.submitList(it)
            }
        }

        val concatAdapter = ConcatAdapter(favoriteListAdapter, adapter.withLoadStateFooter(
            footer = UserListLoadStateAdapter(adapter)
        ))
        binding.rvUserList.adapter = concatAdapter

        lifecycleScope.launchWhenCreated {
            viewModel.pagedList.collectLatest {
                Log.d("TEST", "PagingData $it")
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvUserList.scrollToPosition(0) }
        }
    }

    override fun onClickItem(id: Int) {
        findNavController().navigate(UserListFragmentDirections.actionUserListFragmentToDetailFragment(id))
    }

    override fun onClickFavorite(id: Int, isFavorite: Boolean) {
        lifecycleScope.launch {
            viewModel.updateFavorite(id, !isFavorite)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    companion object {
        val TAG: String = UserListFragment::class.java.name

        @JvmStatic
        fun newInstance() = UserListFragment().apply {}
    }
}