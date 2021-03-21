package com.hossamelsharkawy.base.image

import android.content.Context
import android.widget.ImageView
import com.hossamelsharkawy.base.R


/**
 * Created by Hossam Elsharkawy
on 09/01/20.  time :12:05

 */
class ImageLoading @JvmOverloads constructor(
    context: Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseImageLoading(context, attrs, defStyleAttr) {

    override val img = ImageView(context).apply {
        val srcCompat = getResourceId(R.styleable.image_loading_srcCompat)
        if (srcCompat != 0)
            setImageResource(srcCompat)
    }

    init {
        addView(img)
    }

}

