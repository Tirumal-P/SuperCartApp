package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.model.PaymentType
import com.example.supercartapp.model.local.preferences.LoginPreferences
import com.example.supercartapp.model.remote.request.AddAddressRequest
import com.example.supercartapp.model.remote.request.DeliveryAddress
import com.example.supercartapp.model.remote.request.Item
import com.example.supercartapp.model.remote.request.PlaceOrderRequest
import com.example.supercartapp.model.remote.response.Address
import com.example.supercartapp.model.remote.response.PlaceOrderResponse
import com.example.supercartapp.repository.DeliveryRepository
import com.example.supercartapp.repository.OrderRepository
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    val deliveryRepository: DeliveryRepository,
    val orderRepository: OrderRepository
) : ViewModel() {

    private val _addressListState = MutableLiveData<UiState<List<Address>>>()
    val addressListState: LiveData<UiState<List<Address>>>
        get() = _addressListState

    private val _addAddressState = MutableLiveData<UiState<String>>()
    val addAddressState: LiveData<UiState<String>>
        get() = _addAddressState

    private val _selectedAddress = MutableLiveData<Address>()
    val selectedAddress: LiveData<Address>
        get() = _selectedAddress

    private val _selectedPaymentType = MutableLiveData<PaymentType?>()
    val selectedPaymentType: LiveData<PaymentType?>
        get() = _selectedPaymentType

    fun onSelectPaymentType(paymentType: PaymentType) {
        _selectedPaymentType.value = paymentType
    }

    private val _placeOrderState = MutableLiveData<UiState<PlaceOrderResponse>>()
    val placeOrderState: LiveData<UiState<PlaceOrderResponse>>
        get() = _placeOrderState

    private val userId: Long =
        LoginPreferences.getUserId().toLong()

    fun getUserAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _addressListState.postValue(UiState.Loading)
                val response = deliveryRepository.getUserAddresses(userId.toInt())
                if (response.status == 0) {
                    _addressListState.postValue(UiState.Success(response.addresses))
                } else {
                    _addressListState.postValue(UiState.Error(response.message))
                }
            } catch (e: Exception) {
                _addressListState.postValue(UiState.Error(e.message ?: "Something went wrong"))
            }
        }
    }

    fun addAddress(addressTitle: String, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _addAddressState.postValue(UiState.Loading)
                val addAddress = AddAddressRequest(
                    userId = userId.toInt(),
                    title = addressTitle,
                    address = address
                )
                val response = deliveryRepository.addUserAddress(addAddress)
                if (response.status == 0) {
                    _addAddressState.postValue(UiState.Success(response.message))
                } else {
                    _addAddressState.postValue(UiState.Error(response.message))
                }
            } catch (e: Exception) {
                _addAddressState.postValue(UiState.Error(e.message ?: "Something went wrong"))
            }
        }
    }

    fun onSelectAddress(address: Address) {
        _selectedAddress.value = address
    }

    fun placeOrder(cartItems: List<CartItemEntity>, total: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _placeOrderState.postValue(UiState.Loading)
                val items = mutableListOf<Item>()
                cartItems.forEach {
                    items.add(Item(it.productId.toInt(), it.productQuantity, it.productPrice))
                }
                val deliveryAddress = DeliveryAddress(selectedAddress.value?.title?:" ", selectedAddress.value?.address?:" ")
                val orderRequest = PlaceOrderRequest(
                    userId = userId.toInt(), deliveryAddress = deliveryAddress, items = items,
                    billAmount = total,
                    paymentMethod = "COD"
                )
                val response = orderRepository.placeOrder(orderRequest)

                if (response.orderId != null) {
                    _placeOrderState.postValue(UiState.Success(response))
                } else {
                    _placeOrderState.postValue(UiState.Error(response.message))
                }
            } catch (e: Exception) {
                _placeOrderState.postValue(
                    UiState.Error(e.message ?: "Something went wrong")
                )
            }
        }
    }

    class CheckoutViewModelFactory(
        val deliveryRepository: DeliveryRepository,
        val orderRepository: OrderRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
                return CheckoutViewModel(deliveryRepository, orderRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel for type CheckoutViewModel")
        }
    }
}