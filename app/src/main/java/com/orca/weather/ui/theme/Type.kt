package com.orca.weather.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.orca.weather.R

// Set of Material typography styles to start with
val UrbanistLight = FontFamily(Font(R.font.urbanist_light))
val UrbanistRegular = FontFamily(Font(R.font.urbanist_regular))
val UrbanistBold = FontFamily(Font(R.font.urbanist_bold))

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = UrbanistBold,
        fontSize = 88.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = UrbanistBold,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    body1 = TextStyle(
        fontFamily = UrbanistLight,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    body2 = TextStyle(
        fontFamily = UrbanistBold,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    caption = TextStyle(
        fontFamily = UrbanistRegular,
        fontSize = 10.sp,
        letterSpacing = 0.sp
    )
)