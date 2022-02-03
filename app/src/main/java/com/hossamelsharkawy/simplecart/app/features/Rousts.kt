package com.hossamelsharkawy.simplecart.app.features

import android.content.Context
import android.content.Intent
import androidx.annotation.VisibleForTesting


fun launchDetailsActivity(context: Context, item: Any) {
    context.startActivity(createDetailsActivityIntent(context, item))
}

@VisibleForTesting
fun createDetailsActivityIntent(context: Context, item: Any) =
    Intent(context, DetailsActivity::class.java)

typealias OnExploreItemClicked = (Any) -> Unit


/*
val moveToDetails: OnExploreItemClicked = {
    launchDetailsActivity(
        context = this,
        item = it
    )
}
*/
