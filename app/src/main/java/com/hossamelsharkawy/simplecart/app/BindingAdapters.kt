/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hossamelsharkawy.simplecart.app

import android.view.View
import androidx.databinding.BindingAdapter
import com.hossamelsharkawy.base.extension.visibleIf
import com.hossamelsharkawy.base.image.BaseImageLoading
import com.hossamelsharkawy.base.net.load


@BindingAdapter("imageUrl")
fun loadImage(view: BaseImageLoading, url: String?) {
    if (!url.isNullOrEmpty()) {
        view.load(url)
    }
}


@BindingAdapter("viewIf")
fun viewIf(view: View, boolean: Boolean?) = view.visibleIf(boolean)


@BindingAdapter("onClick")
fun onClick(view: View, action: () -> Unit) = view.setOnClickListener { action.invoke() }







