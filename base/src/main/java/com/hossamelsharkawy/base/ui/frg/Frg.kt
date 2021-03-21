package com.hossamelsharkawy.base.ui.frg

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class Frg<V : ViewModel>(@LayoutRes override val layoutId: Int) : BaseFrg(layoutId) {

    lateinit var viewModel: V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this@Frg).get(getViewModelClass())
    }

    // index of 0 means first argument of BaseActivity class param
    private fun getViewModelClass() =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>


    fun <T> observe(l: LiveData<T>, function: (T) -> Unit) {
        l.observe(this@Frg, function)
    }


}