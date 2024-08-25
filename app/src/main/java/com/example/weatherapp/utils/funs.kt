package com.example.weatherapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.weatherapp.R


fun hexToColorInt(hex: String): Color {
    val cleanedHex = if (hex.startsWith("#")) {
        hex.substring(1)
    } else {
        hex
    }

    val colorHex = if (cleanedHex.length == 6) {
        "FF$cleanedHex"
    } else {
        cleanedHex
    }

    val colorInt = colorHex.toLong(16).toInt()

    return Color(colorInt)
}

fun getCelciusFromKelvin(kelvin: Double): Double {
    val celsius = kelvin - 273.15
    return String.format("%.2f", celsius).toDouble()
}

@Composable
fun getWeatherImages(weatherDescription: String): Painter {
    return when {
        weatherDescription.contains("cloud", ignoreCase = true) ||
                weatherDescription.contains(
                    "nubes",
                    ignoreCase = true
                ) -> painterResource(id = R.drawable.day_clouds)

        weatherDescription.contains("rain", ignoreCase = true) ||
                weatherDescription.contains(
                    "lluvia",
                    ignoreCase = true
                ) -> painterResource(id = R.drawable.day_rain)

        weatherDescription.contains("storm", ignoreCase = true) ||
                weatherDescription.contains(
                    "tormenta",
                    ignoreCase = true
                ) -> painterResource(id = R.drawable.day_storm)

        weatherDescription.contains("sun", ignoreCase = true) ||
                weatherDescription.contains(
                    "sol",
                    ignoreCase = true
                ) -> painterResource(id = R.drawable.day_sun)

        weatherDescription.contains("wind", ignoreCase = true) ||
                weatherDescription.contains(
                    "viento",
                    ignoreCase = true
                ) -> painterResource(id = R.drawable.day_wind)

        else -> painterResource(id = R.drawable.day_snow)
    }
}