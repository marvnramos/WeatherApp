package com.example.weatherapp.ui.theme

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun ThemeSwitcher() {
    var isDarkTheme by remember { mutableStateOf(false) }

    WeatherAppTheme(darkTheme = isDarkTheme) {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = { isDarkTheme = !isDarkTheme }) {
                    val imageVector = if(isDarkTheme){
                        Icons.Filled.DarkMode
                    }else{
                        Icons.Filled.LightMode
                    }
                    Icon(
                        imageVector = imageVector,
                        contentDescription = if (isDarkTheme) "Switch to Light Theme" else "Switch to Dark Theme"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewThemeSwitcher() {
    ThemeSwitcher()
}