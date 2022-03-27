package com.hossamelsharkawy.simplecart.app.ui.theme


import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.hossamelsharkawy.simplecart.R



object MyFont{
     val Cairo = FontFamily(
        Font(R.font.cairo_black, FontWeight.Black),
        Font(R.font.cairo_bold, FontWeight.Bold),
        Font(R.font.cairo_extra_bold, FontWeight.ExtraBold),
        Font(R.font.cairo_light, FontWeight.Light),
        Font(R.font.cairo_regular, FontWeight.Normal),
        Font(R.font.cairo_medium, FontWeight.Medium)
    )

     val Karla = FontFamily(
        Font(R.font.karla_regular, FontWeight.Normal),
        Font(R.font.karla_bold, FontWeight.Bold),
    )

     val Roboto = FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_black, FontWeight.Black)
    )
     val Montserrat = FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_black, FontWeight.Black),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_bold, FontWeight.Bold)
    )

}


private val fontSize1 = 13.sp
private val fontSize2 = 15.sp
private val fontSize3 = 19.sp
private val fontSize4 = 40.sp
private val fontSize6 = 30.sp

val MyTypography = Typography(
    h1 = TextStyle(
        fontFamily = MyFont.Montserrat,
        fontSize = fontSize2,
        fontWeight = FontWeight.Medium,
        color =MyColor.BottomNavigationBackgroundColor,

        //  lineHeight = 18.sp,
        //letterSpacing = (-0.5).sp ,
        textAlign = TextAlign.Center,
        textDirection = TextDirection.Content,
    ),
    h2 = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = fontSize2,
        fontWeight = FontWeight.Medium,
        color =MyColor.BlueDark,

        // lineHeight = 73.sp,
        // letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontFamily = MyFont.Karla,
        fontSize = fontSize3,
        fontWeight = FontWeight.Bold,
        lineHeight = fontSize2,

        ),
    h4 = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = fontSize4,
        color =MyColor.BlueDark,

        fontWeight = FontWeight.Medium,
           lineHeight = 1.sp
    ),
    h5 = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = fontSize1,
        fontWeight = FontWeight.SemiBold,
        //   lineHeight = 29.sp
    ),
    h6 = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = fontSize6,
        fontWeight = FontWeight.SemiBold,
        //  lineHeight = 24.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = MyFont.Roboto,
        fontSize = fontSize2,
        fontWeight = FontWeight.Medium,
        //  lineHeight = 15.sp,
        // letterSpacing = (-1.5).sp,
        // lineHeight = 14.sp,
        color = Color(0xFF685454),
    ),
    subtitle2 = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 14.sp,
        letterSpacing = 0.1.sp,
        color = Color(0xFF5E5E5E),
    ),
    body1 = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        //  lineHeight = 28.sp,
        letterSpacing = 0.15.sp
    ),
    body2 = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        //   lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        // lineHeight = 16.sp,
        //  letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = MyFont.Cairo,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        color = Color(0xFFFF0000),
        fontFamily = MyFont.Cairo,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 16.sp,
        letterSpacing = 1.sp
    )
)
