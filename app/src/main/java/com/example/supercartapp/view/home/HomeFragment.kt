package com.example.supercartapp.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentHomeBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.repository.SuperCartRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hide
import com.example.supercartapp.util.show
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
//        homeViewModel.categories.observe(viewLifecycleOwner){
//            categoryAdapter.submitList(it)
//        }
//
//        homeViewModel.processLoading.observe(viewLifecycleOwner){
//            if(it) {
//                binding.pbCategoryProgress.show()
//                binding.tvErrorText.show()
//            }
//            else {
//                binding.pbCategoryProgress.hide()
//                binding.tvErrorText.show()
//            }
//        }
//
//        homeViewModel.errorLiveData.observe(viewLifecycleOwner){
//            binding.tvErrorText.text = it
//        }
        with(binding) {
            homeViewModel.categories.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Success -> {
                        categoryAdapter.submitList(it.data)
                        tvErrorText.hide()
                        pbCategoryProgress.hide()
                        rvCategoryList.show()
                    }

                    is UiState.Loading -> {
                        tvErrorText.hide()
                        pbCategoryProgress.show()
                    }

                    is UiState.Error -> {
                        tvErrorText.text = it.message
                        tvErrorText.show()
                        pbCategoryProgress.hide()
                        rvCategoryList.hide()
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategoryList.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

}