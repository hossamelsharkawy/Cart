package com.hossamelsharkawy.base.ui.frg

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import com.hossamelsharkawy.base.R
import com.hossamelsharkawy.base.extension.launchOnUI
import com.hossamelsharkawy.loadingview.LoadingView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

abstract class FrgReq<V : ViewModel>(@LayoutRes override val layoutId: Int) : Frg<V>(layoutId) {

    var delayLoading: Long? = null
    private var loadingJob: Job? = null
    var loadingView: LoadingView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView = bind(R.id.loading_view)
    }

    fun showLoading() {
        loadingJob?.cancel()
        loadingJob = launchOnUI {
            delay(delayLoading ?: 0)
            loadingView?.showLoading()
        }
    }

    fun showContent() {
        loadingJob?.cancel()
        loadingJob = launchOnUI {
            loadingView?.showContent()
        }
    }

    open fun showError(msg: String? = null, onRetry: (() -> Unit)? = null) {
        loadingJob?.cancel()
        loadingJob = launchOnUI {
            loadingView?.showError(msg, onRetry)
        }
    }


    override fun onPause() {
        loadingJob?.cancel()
        super.onPause()
    }


}