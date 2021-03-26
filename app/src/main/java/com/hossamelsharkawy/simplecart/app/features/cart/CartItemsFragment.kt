package com.hossamelsharkawy.simplecart.app.features.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.CornerFamily
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.features.products.ProductItemClickListener
import com.hossamelsharkawy.simplecart.app.features.products.ProductViewModel
import com.hossamelsharkawy.simplecart.app.features.products.withFragment
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.databinding.BottomSheetPersistentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CartItemsFragment : BottomSheetDialogFragment() {

    lateinit var viewBinding: BottomSheetPersistentBinding
    private lateinit var mViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_persistent, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = viewBinding.bottomSheet

        view.setBackgroundResource(R.color.gray_xxxxx)
        bottomSheet.shapeAppearanceModel = bottomSheet.shapeAppearanceModel
            .toBuilder()
            .setTopLeftCorner(CornerFamily.ROUNDED, 70f)
            .setTopRightCorner(CornerFamily.ROUNDED, 70f)
            .build()

        setViewModel()
        setCartItemUi()
    }


    private fun setViewModel() = with(viewBinding) {
        withFragment(this)
        mViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        this.viewModel = mViewModel
    }

    private fun setCartItemUi() = with(viewBinding) {
        val adapter = CartItemsAdapter(object : ProductItemClickListener {

            override fun onPlusQty(product: Product) {
                mViewModel.onPlusQty(product)
            }

            override fun onMinQty(product: Product) {
                mViewModel.onMinQty(product)
            }
        })

        cartItemList.adapter = adapter

        lifecycleScope.launch {
            viewModel?.cartItems?.collect {result ->
                adapter.submitList(result)
            }
        }
    }
}