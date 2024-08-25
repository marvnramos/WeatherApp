package com.example.weatherapp.ui.view

import CardComponent
import TopBarComponent
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.models.CardData
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.example.weatherapp.utils.getCelciusFromKelvin
import com.example.weatherapp.utils.getWeatherImages
import com.example.weatherapp.utils.hexToColorInt


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun MainView(viewModel: WeatherViewModel = viewModel()) {
    val text = remember { MutableLiveData("es") }
    val weather by viewModel.weatherResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    val context = LocalContext.current

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
            },
            timeOver = {
                viewModel.setLanguage("${text.value}")
                Toast.makeText(context, "reloaded", Toast.LENGTH_SHORT).show()
            }
        )
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

                    val celciusDegree = getCelciusFromKelvin(weather!!.main.temp)
                    val min = getCelciusFromKelvin(weather!!.main.temp_min)
                    val max = getCelciusFromKelvin(weather!!.main.temp_max)
                    val humidity = weather!!.main.humidity

                    val cardData = CardData(
                        minTemp = "$min°C",
                        maxTemp = "$max°C",
                        humidity = "$humidity%",
                    )

                    Text(
                        text = "${weather!!.name}, ${weather!!.sys.country}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                    )
                    Image(
                        painter = getWeatherImages(weatherDescription),
                        contentDescription = ""
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${celciusDegree}°C",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
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
                        Spacer(modifier = Modifier.padding(16.dp))

                        CardComponent(cardData)
                    }
                }
            }
        }
    }
}