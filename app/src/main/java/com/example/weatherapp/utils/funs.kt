package com.example.weatherapp.utils

import androidx.compose.ui.graphics.Color


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