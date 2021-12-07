package com.hossamelsharkawy.simplecart.app.features.notification

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private val viewBinding by viewBinding(FragmentNotificationBinding::bind)

    private lateinit var mViewModel: NotificationViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        subUIEvents()
    }

    private fun subUIEvents() = with(mViewModel) {
       /* collect(openCartAction.flow) {
            navigateToNotificationAction.invoke()
        }*/
    }


    private fun setViewModel() = with(viewBinding) {
        mViewModel = ViewModelProvider(requireActivity())[NotificationViewModel::class.java]
        withFragment(this)
        this.viewModel = mViewModel
    }


}

fun Fragment.withFragment(dataBinding: ViewDataBinding) {
    dataBinding.lifecycleOwner = this.viewLifecycleOwner
}




