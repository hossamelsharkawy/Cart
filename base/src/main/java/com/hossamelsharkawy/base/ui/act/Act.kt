package com.hossamelsharkawy.base.ui.act

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.hossamelsharkawy.base.R
import com.hossamelsharkawy.base.extension.asyncAwait
import com.hossamelsharkawy.base.lang.LocaleHelper
import com.hossamelsharkawy.base.extension.launchOnUI
import com.hossamelsharkawy.base.lang.LangUtils
import com.hossamelsharkawy.base.net.BaseApp
import com.hossamelsharkawy.base.utils.Exe
import kotlinx.coroutines.Job

abstract class Act : AppCompatActivity(), View.OnClickListener {
    abstract var layout: Int
    var reqJob: Job? = null
    var reqJob2: Job? = null

    var isView = false

    open fun onReView() {}

    override fun onResume() {
        super.onResume()
        if (isView)
            onReView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // overridePendingTransition(0, 0)
        // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        reqJob = launchOnUI {
            try {
                LocaleHelper.setLocale(this@Act, BaseApp.lang)
                setContentView(layout)
                asyncAwait {
                    isView = true
                    onView()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                setContentView(R.layout.error_view)

                if (e.stackTrace.isNotEmpty()){
                    val error = e.stackTrace.get(0)

                    findViewById<TextView>(R.id.txt_error_msg).text = "Class :${error?.fileName} \n" +
                            "Method :${error?.methodName}\n" +
                            "Line :${error?.lineNumber} \n" +
                            "Exception :${e.javaClass.simpleName} \n" +
                            "Error :${e.fillInStackTrace() ?: "Unknown"}"

                }else {
                    findViewById<TextView>(R.id.txt_error_msg).text = e.stackTraceToString()
                }

                e.printStackTrace()
                findViewById<View>(R.id.btn_tryAgain)
                .setOnClickListener {
                    Exe.alertDialog(this@Act,
                        "Error",
                        "Try delete cache ?"
                    ) { _, _ -> BaseApp.onCrashAction() }.show()

                }

            }

        }

    }

    override fun onDestroy() {
        reqJob?.cancel()
        reqJob2?.cancel()
        super.onDestroy()
    }


    abstract fun onView()

    fun getIntEX(key: String) = intent?.extras?.get(key) as Int
    fun getStringEX(key: String) = intent?.extras?.get(key) as String
    fun getBolEX(key: String) = intent?.extras?.get(key) as Boolean
    fun <T : Parcelable?> getParc(key: String) = intent?.getParcelableExtra<T>(key)
    fun <T : Parcelable?> getParcArray(key: String) = intent?.getParcelableArrayListExtra<T>(key)
    fun <T> getSerializ(key: String) = intent?.getSerializableExtra(key) as T


    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        // overridePendingTransition(0, R.anim.fade_out)
        // overridePendingTransition(0, 0)
        //   overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

    }


    fun setRtl() {
        window.decorView.layoutDirection =
            if (BaseApp.lang == LangUtils.AR) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    fun <T> getFrg(frgId: Int) = supportFragmentManager.findFragmentById(frgId) as T

    /*************************************************************/

    protected fun click(@IdRes vararg ids: Int) {
        for (id in ids) {
            click(id)
        }
    }

    protected fun click(vararg views: View) {
        for (view in views) {
            click(view)
        }
    }


    protected fun <T : View> bind(id: Int) = findViewById<View>(id) as T

    fun click(id: Int) = findViewById<View>(id).setOnClickListener(this)


    fun click(id: Int, function: () -> Unit) = findViewById<View>(id).setOnClickListener {
        function()
    }

    protected fun click(view: View?) = view?.setOnClickListener(this)

    override fun onClick(v: View?) {

    }


}
