package com.hossamelsharkawy.base.extension

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.navigation.NavController
import com.google.android.material.tabs.TabLayout
import com.hossamelsharkawy.base.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/**
 * Created by Hossam Elsharkawy
0201099197556
on 29/08/19.  time :18:35

 */
fun EditText.onValueChanged(onChange: (String) -> Unit) {
    var xxx: Job? = null
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            xxx?.cancel()
            xxx = launchOnUI {
                delay(250)
                onChange.invoke(s.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            xxx?.cancel()
        }
    })
}


fun EditText.setValidEditText() {
    setBackgroundResource(R.drawable.line_bottom)
}

fun EditText.intOrZero(): Int {
    val s = text.toString()
    return if (s.isEmpty()) 0 else s.toInt()
}


fun TabLayout.setOnChanged(function: (pos: Int) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {

        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(p0: TabLayout.Tab) {
            function.invoke(p0.position)
        }
    })
}


fun EditText.sub(delay: Long? = 300, runnable: Runnable) {
    addTextChangedListener(MyWatcher(runnable, delay))
}

class MyWatcher(private val runnable: Runnable, private val delay: Long? = 300) : TextWatcher {
    private val handlerThread = Handler(Looper.getMainLooper())
    override fun afterTextChanged(s: Editable?) {
        handlerThread.removeCallbacks(runnable)
        handlerThread.postDelayed(runnable, delay ?: 10)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

fun String?.isNotShort(min: Int) = this?.length ?: 0 > min
fun android.view.View.showKeyBoard(context: Context) =
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager)
        .showSoftInput(this, 0)

fun android.view.View.hideKeyBoard(context: Context) =
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager)
        .hideSoftInputFromWindow(this.windowToken, 0)


fun NavController.navigateSafe(@IdRes id: Int) {
    currentDestination?.getAction(id)
        ?.let { navigate(id) }
}


