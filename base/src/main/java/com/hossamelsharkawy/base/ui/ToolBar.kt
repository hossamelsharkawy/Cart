package com.hossamelsharkawy.base.ui

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.hossamelsharkawy.base.R

/**
 * Created by Hossam Elsharkawy
0201099197556
on 7/10/2018.  time :15:19
 */
open class ToolBar(private val act: Activity) {
    private lateinit var imgBack: View
    private lateinit var toolBar: View
    private lateinit var txtTitle: TextView
    private lateinit var txtSubTitle: TextView

    fun title(title: String) = apply {
        this.txtTitle = this.act.findViewById(R.id.txt_titleToolBar)
        imgBack = act.findViewById<View>(R.id.img_BackToolBar)
        this.txtTitle.text = title
    }

    open fun title(@StringRes titleRes: Int) = apply {
        this.txtTitle = this.act.findViewById(R.id.txt_titleToolBar)
        imgBack = act.findViewById<View>(R.id.img_BackToolBar)
        this.txtTitle.text = this.act.getString(titleRes)
    }

    fun subTitle(subTitle: String) = apply {
        this.txtSubTitle = this.act.findViewById(R.id.txt_subTitleToolBar)
        this.txtSubTitle.text = subTitle
        this.txtSubTitle.visibility = View.VISIBLE
    }

    fun toolBarColor(backgroundColor: Int) = apply {
        this.toolBar = this.act.findViewById(R.id.tool_bar)
        this.toolBar.setBackgroundResource(backgroundColor)
    }

    fun backListener(backListener: () -> Unit) = apply {
        imgBack = act.findViewById<View>(R.id.img_BackToolBar)
        imgBack.visibility = View.VISIBLE
        imgBack.setOnClickListener { backListener.invoke() }
    }



    private lateinit var search: View


}

