package com.hossamelsharkawy.base.ui.frg

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract  class BaseFrg(@LayoutRes open val layoutId: Int)  :Fragment(){

    var rootView: View? = null

 /*   override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false).apply {
        this@BaseFrg.rootView = this
    }
*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment or reuse the existing one
        rootView = if (rootView != null) rootView else inflater.inflate(
            layoutId,
            container,
            false
        )
        return rootView
    }

    fun <T : View> bind(id: Int): T? = rootView?.findViewById(id)

    fun getIntEX(key: String) = arguments?.getInt(key)

    fun getStringEX(key: String) = arguments?.getString(key)
    fun getBolEX(key: String) = arguments?.getBoolean(key)
    fun <T : Parcelable?> getParc(key: String) = arguments?.getParcelable<T>(key)
    fun <T : Parcelable?> getParcArray(key: String) = arguments?.getParcelableArrayList<T>(key)
    fun <T> getSerializ(key: String): T? {
        val v = arguments?.getSerializable(key)

        return if (v != null) {
            v as T
        } else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // keep the fragment and all its data across screen rotation
        retainInstance = true
    }
}