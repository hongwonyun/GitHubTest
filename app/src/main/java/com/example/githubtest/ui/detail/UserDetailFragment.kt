package com.example.githubtest.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.githubtest.R
import com.example.githubtest.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding: FragmentUserDetailBinding get() = _binding!!
    private val viewModel: UserDetailViewModel by viewModels { defaultViewModelProviderFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.getItem().collectLatest {
                binding.data = it

                it?.let { data ->
                    binding.btnFavorite.setOnClickListener {
                        lifecycleScope.launch {
                            data.isFavorite = !data.isFavorite
                            data.id?.let { it1 -> viewModel.updateFavorite(it1, data.isFavorite) }
                            binding.invalidateAll()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }



    companion object {
        const val KEY_ID = "id"

        @JvmStatic
        fun newInstance() = UserDetailFragment().apply {}
    }
}