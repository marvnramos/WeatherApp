package com.example.weatherapp.ui.view

import TopBarComponent
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.example.weatherapp.utils.hexToColorInt


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun MainActivity(viewModel: WeatherViewModel = viewModel()) {
    val text = remember { MutableLiveData("es") }
    val weather by viewModel.weatherResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    Scaffold(
        topBar = { },
        floatingActionButton = { },
    ) {
        TopBarComponent(text = text,
            onLanguageChanged = {
                if (text.value == "es") {
                    viewModel.setLanguage("es")
                } else {
                    viewModel.setLanguage("en")
                }
            })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp)
                .verticalScroll(rememberScrollState())

        ) {
            when {
                isLoading -> {
                    Text(text = "Loading...")
                }

                errorMessage != null -> {
                    Text(text = errorMessage!!)
                }

                weather != null -> {
                    val weatherDescription = weather!!.weather[0].description.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    }

                    Image(
                        painter = painterResource(id = R.drawable.day_rain),
                        contentDescription = ""
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = weatherDescription,
                            modifier = Modifier
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            hexToColorInt("#1F316F"),
                                            Color(0xFF1976D2)
                                        )
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(8.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}