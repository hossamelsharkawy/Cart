package com.hossamelsharkawy.base.image

import android.content.Context
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.hossamelsharkawy.base.R


/**
 * Created by Hossam Elsharkawy
on 09/01/20.  time :12:05

 */
class ImageShapLoading @JvmOverloads constructor(
    context: Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseImageLoading(context, attrs, defStyleAttr) {

    private var cornerSize = resources.getDimension(R.dimen._10sdp)
    //private var  elevationV =resources.getDimension(R.dimen.elevationValue2)

    override val img = ShapeableImageView(context, null, R.style.circleImageViewRounded).apply {

        //strokeWidth = resources.getDimension(R.dimen._1sdp)
        //strokeColor =  ColorStateList.valueOf(Color.LTGRAY)
        setBackgroundColor(resources.getColor(R.color.white))
        //   elevation = elevationV

        shapeAppearanceModel = shapeAppearanceModel
            .toBuilder()
            //.setTopRightCorner(CornerFamily.ROUNDED,50f)
            .setAllCorners(CornerFamily.ROUNDED, cornerSize)
            .build()

        val srcCompat = getResourceId(R.styleable.image_loading_srcCompat)
        if (srcCompat != 0)
            setImageResource(srcCompat)
    }


    init {
        addView(img)
    }


}

