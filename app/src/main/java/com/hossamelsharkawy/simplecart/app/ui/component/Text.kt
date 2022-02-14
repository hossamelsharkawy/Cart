package com.hossamelsharkawy.simplecart.app.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.hossamelsharkawy.simplecart.app.ui.theme.MyFont
import com.hossamelsharkawy.simplecart.app.ui.theme.MyTypography

@Composable
fun MyTitle(
    text: String?,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MyTypography.h1,
    maxLines: Int = 1,
) {
    Text(
        style = style,
        text = text ?: "...",
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        modifier = modifier
    )
}

@Composable
fun MyTitleCenter(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MyTypography.h1,
) {
    MyTitle(text, modifier, TextAlign.Center, style)
}

@Composable
fun MyTitleCenter2(
    text: String, modifier: Modifier = Modifier,
    style: TextStyle = MyTypography.h3,
) {
    MyTitleCenter(text, modifier, style)
}

@Composable
fun MyNumber(
    text: Any,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
) {
    val subtitle1 = TextStyle(
        fontFamily = MyFont.Roboto,
        fontWeight = FontWeight.Medium,
        //  lineHeight = 15.sp,
        // letterSpacing = (-1.5).sp,
        // lineHeight = 14.sp,
        color = Color(0xFF685454),
    )
    Text(
        style = subtitle1,
        text = text.toString(),
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = modifier
    )
}

@Composable
fun MyNumberCenter(
    text: Any,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) {
    MyNumber(text, modifier, textAlign)
}

@Composable
fun MyPrice(
    text: Any,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) {
    MyNumber(text, modifier, textAlign)
}


@Composable
fun MySubtitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {


    Text(
        style = MyTypography.subtitle1,
        text = text,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = modifier
    )
}

@Composable
fun MySubtitle2(
    text: String,

    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center

) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = 1,
        textAlign = textAlign,
        style = MyTypography.subtitle2

    )
}

@Composable
fun MySubtitle3(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier,
        style = TextStyle(
            textAlign = TextAlign.Center
        )
    )
}
