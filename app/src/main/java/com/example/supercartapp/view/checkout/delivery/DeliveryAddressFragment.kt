package com.example.supercartapp.view.checkout.delivery

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.DialogAddAddressBinding
import com.example.supercartapp.databinding.FragmentDeliveryBinding
import com.example.supercartapp.model.local.model.AddressUiItem
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.model.remote.response.Address
import com.example.supercartapp.repository.DeliveryRepositoryImpl
import com.example.supercartapp.repository.OrderRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.CheckoutViewModel

class DeliveryAddressFragment :
    Fragment(R.layout.fragment_delivery) {
    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var deliveryAddressAdapter: DeliveryAddressAdapter
    private var currentAddresses: List<Address> = emptyList()
    private var selectedAddress: Address? = null
    private var addAddressDialog: Dialog? = null
    private val checkoutViewModel: CheckoutViewModel by navGraphViewModels(R.id.nav_checkout) {
        val deliveryRepository = DeliveryRepositoryImpl(ApiClient.apiService)
        val orderRepository = OrderRepositoryImpl(ApiClient.apiService)
        CheckoutViewModel.CheckoutViewModelFactory(deliveryRepository, orderRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeliveryBinding.bind(view)
        setUpView()
        setUpObservers()
        setUpEventHandling()
        checkoutViewModel.getUserAddress()
    }

    private fun setUpView() {
        deliveryAddressAdapter =
            DeliveryAddressAdapter { address ->
                onAddressClick(address)
            }
        binding.rvAddressList.apply {
            adapter = deliveryAddressAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.header.tvDeliveryStep.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary))
    }

    private fun setUpEventHandling() {
        with(binding) {
            btnAddAddress.setOnClickListener {
                showAddAddressDialog()
            }
            btnDeliveryNext.setOnClickListener {
                if (selectedAddress == null) {
                    Toast.makeText(
                        requireContext(),
                        "Please select a delivery address",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                findNavController().navigate(R.id.action_deliveryAddressFragment_to_paymentFragment)
            }
        }
    }

    private fun setUpObservers() {
        checkoutViewModel.addressListState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                when (state) {
                    is UiState.Loading -> {
                        pbDeliveryAddressProgress.hideRest(
                            tvDeliveryAddressMessage,
                            rvAddressList
                        )
                    }
                    is UiState.Error -> {
                        tvDeliveryAddressMessage.text =
                            state.message

                        tvDeliveryAddressMessage.hideRest(
                            rvAddressList,
                            pbDeliveryAddressProgress
                        )
                    }

                    is UiState.Success -> {
                        currentAddresses = state.data
                        updateAddressUiList()
                        if (state.data.isEmpty()) {
                            tvDeliveryAddressMessage.text = "No delivery address found"
                            tvDeliveryAddressMessage.hideRest(rvAddressList, pbDeliveryAddressProgress)
                        } else {
                            rvAddressList.hideRest(tvDeliveryAddressMessage, pbDeliveryAddressProgress)
                        }
                    }
                }
            }
        }

        checkoutViewModel.selectedAddress.observe(
            viewLifecycleOwner
        ) { address ->
            selectedAddress = address
            updateAddressUiList()
        }

        checkoutViewModel.addAddressState.observe(
            viewLifecycleOwner
        ) { state ->
            when (state) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    addAddressDialog?.dismiss()
                    checkoutViewModel.getUserAddress()
                }

                is UiState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateAddressUiList() {
        val uiList =
            currentAddresses.map { address ->
                AddressUiItem(address = address,
                    isSelected = address.addressId == selectedAddress?.addressId
                )
            }
        deliveryAddressAdapter.submitList(uiList)
    }

    private fun onAddressClick(address: Address) {
        checkoutViewModel.onSelectAddress(address)
    }

    private fun showAddAddressDialog() {
        val dialog =
            Dialog(requireContext())
        addAddressDialog = dialog
        val dialogBinding =
            DialogAddAddressBinding.inflate(layoutInflater)
        dialog.setContentView(
            dialogBinding.root
        )
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnSave.setOnClickListener {
            val addressTitle = dialogBinding.etAddressTitle.text.toString().trim()

            val address = dialogBinding.etAddress.text.toString().trim()

            if (addressTitle.isEmpty()) {
                dialogBinding.etAddressTitle.error = "Please enter address title"
                return@setOnClickListener
            }

            if (address.isEmpty()) {
                dialogBinding.etAddress.error = "Please enter address"
                return@setOnClickListener
            }
            checkoutViewModel.addAddress(
                addressTitle,
                address
            )
        }

        dialog.show()

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}