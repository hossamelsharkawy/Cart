package com.hossamelsharkawy.simplecart.app.features.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossamelsharkawy.base.extension.*
import com.hossamelsharkawy.base.utils.Exe
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import com.hossamelsharkawy.simplecart.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
) : ViewModel() {

    val itemsCount = 0.vmStateShort(this)
    val dataLoading = true.vmStateShort(this)
    val items = arrayListOf<Product>().vmStateShort(this)
    val cartItems = arrayListOf<Product>().vmStateShort(this)


}







