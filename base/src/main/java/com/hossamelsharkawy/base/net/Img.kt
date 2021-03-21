package com.hossamelsharkawy.base.net

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hossamelsharkawy.base.R
import com.hossamelsharkawy.base.image.BaseImageLoading
import com.hossamelsharkawy.base.image.ImageLoading
import com.hossamelsharkawy.base.image.ImageShapLoading
import com.squareup.picasso.Callback
import com.squareup.picasso.LruCache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * Created by Hossam Elsharkawy
0201099197556
on 8/27/2018.  time :11:31

 */
object Img {


    val picasso: Picasso by lazy {
        with(BaseApp.app) {

            val builder = Picasso.Builder(this)
            val okHttp3Client = OkHttpClient.Builder()
                .connectTimeout(250L, TimeUnit.SECONDS)
                .writeTimeout(250L, TimeUnit.SECONDS)
                .readTimeout(250L, TimeUnit.SECONDS)
                .build()
            builder.downloader(OkHttp3Downloader(okHttp3Client))
            //             builder.downloader(OkHttp3Downloader(BaseApp.client))
            // builder.downloader()
            //builder.loggingEnabled(true)
            //  builder.indicatorsEnabled(true)

            // builder.memoryCache(Cache(cacheDir, 25 * 1024 * 1024))
            builder.memoryCache(LruCache(this))
            // builder.cache(new Cache(context.getCacheDir(), 25 * 1024 * 1024))

           /* val built = builder.build()
            built.setIndicatorsEnabled(true)
            built.isLoggingEnabled = true
            Picasso.setSingletonInstance(built)
*/
            builder.build()
            //Picasso.with(this)

        }

    }
}

fun ImageView.load(src: String?, placeholderId: Int = BaseApp.logo) {
    Img.picasso.load(src).placeholder(placeholderId).fit().into(this)
}

fun ImageView.loadProfile(src: String?, placeholderId: Int = BaseApp.logo) {
    Img.picasso.load(src).resizeDimen(R.dimen._90sdp, R.dimen._90sdp).placeholder(placeholderId)
        .centerInside().into(this)
}

fun ImageView.loadProfile(src: java.io.File, placeholderId: Int = BaseApp.logo) {
    Img.picasso.load(src).resizeDimen(R.dimen._90sdp, R.dimen._90sdp).placeholder(placeholderId)
        .centerInside().into(this)
}

fun ImageLoading.load(src: String?, placeholderId: Int = BaseApp.logo) {
    if (src == null) {
        hide()
    } else {
        loading()
    }
    Img.picasso
        .load(src)
        // .placeholder(placeholderId)
        .resizeDimen(R.dimen._80sdp, R.dimen._80sdp)
        .into(this.img, object : Callback {
            override fun onSuccess() {
                hide()
            }

            override fun onError(e: Exception?) {
                hide()
            }
        })
}

fun BaseImageLoading.loadBackground(src: String?, placeholderId: Int = BaseApp.logo) {
    if (src == null) {
        hide()
    } else {
        loading()
    }

    Img.picasso
        .load(src)
        //.placeholder(placeholderId)
        .resizeDimen(R.dimen._250sdp, R.dimen._150sdp)
        // .centerCrop()
        .error(placeholderId)
        //.centerInside()
        .into(this.img, object : Callback {
            override fun onSuccess() {
                hide()
            }

            override fun onError(e: Exception?) {
                hide()
            }
        })
}

fun ImageLoading.loadBackground2(src: String?, placeholderId: Int = BaseApp.logo) {
    if (src == null) {
        hide()
    } else {
        loading()
    }
    Img.picasso
        .load(src)
        //  .placeholder(placeholderId)
        //   .resizeDimen(R.dimen._315sdp, R.dimen._150sdp)
        .fit()
        .centerCrop()
        .error(R.drawable.ic_baseline_android_24)
        .into(this.img, object : Callback {
            override fun onSuccess() {
                hide()
            }

            override fun onError(e: Exception?) {
                hide()
            }
        })
}


fun ImageShapLoading.load(src: String?, placeholderId: Int = BaseApp.logo) {
    if (src == "") return
    loading()
    Img.picasso
        .load(src)
        .placeholder(placeholderId)
        .resizeDimen(R.dimen._80sdp, R.dimen._80sdp)
        .into(this.img, object : Callback {
            override fun onSuccess() {
                hide()
            }

            override fun onError(e: Exception?) {
                hide()
            }
        })

   // Glide.with(this).load(src).into(this.img)

}

fun BaseImageLoading.load(src: String?, placeholderId: Int = BaseApp.logo) {
    loading()
    Img.picasso
        .load(src)
        //   .placeholder(placeholderId)
        .resizeDimen(R.dimen._80sdp, R.dimen._80sdp)
        .centerCrop()
        .into(this.img, object : Callback {
            override fun onSuccess() {
                hide()
            }

            override fun onError(e: Exception?) {
                hide()
            }
        })
}


fun ImageView.loadItem(src: String?, placeholderId: Int = BaseApp.logo) {
    //  Img.picasso.load(src).placeholder(placeholderId).resize(250,250).into(this)

    Img.picasso
        .load(src)
        // .networkPolicy(NetworkPolicy.OFFLINE)
        .placeholder(placeholderId)
        .resizeDimen(R.dimen._80sdp, R.dimen._80sdp)
        .into(this)

}

fun ImageView.load(src: java.io.File, placeholderId: Int = BaseApp.logo) {
    // Img.picasso.load(src).placeholder(placeholderId).fit().centerCrop().into(this)
    Img.picasso.load(src).placeholder(placeholderId).fit().into(this)
}

fun ImageView.load(
    src: String,
    onSuccess: () -> Unit,
    onError: () -> Unit,
    placeholderId: Int = R.drawable.ic_baseline_android_24
) {
    Img.picasso
        .load(src)
        //   .placeholder(placeholderId)
        // .fit()
        // .centerCrop()
        .into(this, object : Callback {
            override fun onSuccess() {
                onSuccess.invoke()
            }

            override fun onError(e: Exception?) {
                onError.invoke()
            }
        })
}