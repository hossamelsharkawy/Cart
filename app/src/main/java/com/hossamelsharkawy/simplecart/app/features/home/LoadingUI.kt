package com.hossamelsharkawy.simplecart.app.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.ui.component.MyCircularProgressIndicator
import com.hossamelsharkawy.simplecart.app.ui.component.MyImage
import com.hossamelsharkawy.simplecart.app.ui.component.MyLinearProgressIndicator
import com.hossamelsharkawy.simplecart.app.ui.theme.MyColor

@Composable
fun LoadingUI(visible: Boolean) {
    if (visible)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MyColor.backgroundColor, shape = RoundedCornerShape(4.dp))
        ) {
          /*  MyLinearProgressIndicator(
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .height(5.dp)
            )
*/


            MyCircularProgressIndicator(
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.Center)
            )

            Image(
                painterResource(id = R.drawable.ic_instacart_logo ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    //.alpha(0.6f)
                    .wrapContentWidth(CenterHorizontally)
                    .height(70.dp)
            )
            /*   MyImage(
                   url = "https://www.instacart.com/assets/beetstrap/brand/instacart-logo-color-4db9d81ca0b7638befdc4bd331f64a2633df790c0b55ef627c99b1ba77af72b7.svg",
                //   url = "https://play-lh.googleusercontent.com/AX4QaQ8NbV3HnSBA7AnZB7F9xj0AsDacwPW09Zp1ZXaxqjzua8wGQIHczgfcFIKsbb4=s180-rw",
                   modifier = Modifier
                       .align(Alignment.Center)
                       .size(250.dp)
               )*/
        }

}

