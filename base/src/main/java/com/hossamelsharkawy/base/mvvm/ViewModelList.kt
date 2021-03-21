package com.hossamelsharkawy.base.mvvm

import androidx.lifecycle.ViewModel
import com.hossamelsharkawy.base.extension.toSingleMutableLiveData

open class ViewModelList<Model> :ViewModel(){

    val data = ArrayList<Model>().toSingleMutableLiveData

}