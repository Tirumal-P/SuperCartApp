package com.example.supercartapp.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentHomeBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.model.remote.response.CategoryItem
import com.example.supercartapp.repository.SuperCartRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val homeViewModel: HomeViewModel by viewModels {
        val repository = SuperCartRepositoryImpl(ApiClient.apiService)
        HomeViewModel.HomeViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setUpRecyclerView()
        setUpObservers()
        homeViewModel.getCategories()
    }

    private fun setUpObservers() {
        with(binding) {
            homeViewModel.categories.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Success -> {
                        categoryAdapter.submitList(it.data)
                        rvCategoryList.hideRest(tvErrorText,pbCategoryProgress)
                    }

                    is UiState.Loading -> {
                        pbCategoryProgress.hideRest(tvErrorText,rvCategoryList)
                    }

                    is UiState.Error -> {
                        tvErrorText.text = it.message
                        tvErrorText.hideRest(pbCategoryProgress,rvCategoryList)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        categoryAdapter = CategoryAdapter({onCategoryClick(it)})
        binding.rvCategoryList.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun onCategoryClick(categoryItem: CategoryItem){
        val action = HomeFragmentDirections.actionHomeFragmentToProductListFragment(
            categoryId = categoryItem.categoryId.toInt()
        )
        findNavController().navigate(action)
    }

}