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
        homeViewModel.categories.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it)
        }

        homeViewModel.processLoading.observe(viewLifecycleOwner){
            if(it) {
                binding.pbCategoryProgress.visibility = View.VISIBLE
                binding.tvErrorText.visibility = View.VISIBLE
            }
            else {
                binding.pbCategoryProgress.visibility = View.GONE
                binding.tvErrorText.visibility = View.VISIBLE
            }
        }

        homeViewModel.errorLiveData.observe(viewLifecycleOwner){
            binding.tvErrorText.text = it
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