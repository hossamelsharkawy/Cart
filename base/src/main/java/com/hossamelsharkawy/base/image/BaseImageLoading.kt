package com.hossamelsharkawy.base.image

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.hossamelsharkawy.base.extension.gone
import com.hossamelsharkawy.base.extension.visible
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.SpriteFactory
import com.github.ybq.android.spinkit.Style
import com.hossamelsharkawy.base.R


/**
 * Created by Hossam Elsharkawy
on 09/01/20.  time :12:05

 */
abstract class BaseImageLoading @JvmOverloads constructor(
    context: Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var progressBar: View? = null

    private fun getPr(): View {
        if (progressBar == null) {
            val d = context.resources.getDimension(R.dimen._30sdp).toInt()
            progressBar =
                SpinKitView(context).apply {
                    layoutParams = LayoutParams(d, d).apply {
                        gravity = Gravity.CENTER
                    }
                    setIndeterminateDrawable(SpriteFactory.create(Style.FADING_CIRCLE).apply {
                        color = context.getColor(R.color.colorPrimary)
                    })
                }
            addView(progressBar)
        }
        return progressBar!!
    }

    private var a = context.theme.obtainStyledAttributes(
        attrs,
        R.styleable.image_loading,
        defStyleAttr, 0
    )

    abstract val img: ImageView

     fun getResourceId(id: Int) = a.getResourceId(id, 0)


    fun loading() {
        getPr().visible()
    }

    fun hide() {
        getPr().gone()
    }


}

