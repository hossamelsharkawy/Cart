package com.hossamelsharkawy.simplecart.app.features.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.CornerFamily
import com.hossamelsharkawy.base.extension.collect
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.features.products.ProductItemClickListener
import com.hossamelsharkawy.simplecart.app.features.products.ProductViewModel
import com.hossamelsharkawy.simplecart.app.features.products.withFragment
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.databinding.BottomSheetPersistentBinding
import com.hossamelsharkawy.simplecart.databinding.FragmentProductsBinding
import kotlinx.coroutines.flow.collect


class CartItemsFragment : BottomSheetDialogFragment() {


    private val viewBinding by viewBinding(BottomSheetPersistentBinding::bind)

    private lateinit var mViewModel: ProductViewModel

    lateinit var adapter: ListAdapter<Product, *>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_sheet_persistent, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
        setCartItemUi()
        subUIEvents()

    }

    private fun subUIEvents() {
        collect(mViewModel.cartItemsFlow) { adapter.submitList(it) }
    }


    private fun setViewModel() = with(viewBinding) {
        withFragment(this)
        mViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        this.viewModel = mViewModel
    }


    private fun setCartItemUi() = with(viewBinding) {
        root.setBackgroundResource(R.color.gray_xxxxx)


        bottomSheet.shapeAppearanceModel = bottomSheet.shapeAppearanceModel
            .toBuilder()
            .setTopLeftCorner(CornerFamily.ROUNDED, 70f)
            .setTopRightCorner(CornerFamily.ROUNDED, 70f)
            .build()



        (dialog as BottomSheetDialog).behavior.state = STATE_EXPANDED

        CartItemsAdapter(object : ProductItemClickListener {
            override fun onPlusQty(product: Product) {
                mViewModel.onPlusQty(product)
            }

            override fun onMinQty(product: Product) {
                mViewModel.onMinQty(product)
            }
        }).run {
            adapter =this
            cartItemList.adapter = this
        }


    }

}