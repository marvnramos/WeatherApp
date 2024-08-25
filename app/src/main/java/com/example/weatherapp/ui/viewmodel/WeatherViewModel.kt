package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.RetrofitInstance
import com.example.weatherapp.data.models.WeatherResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    private val API_KEY = BuildConfig.API_KEY

    private val _weatherResponse = MutableLiveData<WeatherResponse?>()
    val weatherResponse: LiveData<WeatherResponse?> = _weatherResponse

    private val _language = MutableLiveData<String>("es") // Idioma por defecto
    val language: LiveData<String> = _language

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchData()
    }

    fun setLanguage(newLanguage: String) {
        _language.value = newLanguage
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.ApiClient.getWeather(
                    lat = 35.0,
                    lon = 139.0,
                    apiKey = API_KEY,
                    language = _language.value ?: "es"
                )
                data.enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(
                        call: Call<WeatherResponse>,
                        response: Response<WeatherResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            _weatherResponse.value = response.body()
                        } else {
                            _errorMessage.value =
                                "Doesn't work: ${response.code()} ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        _isLoading.value = false
                        _errorMessage.value = "Failed to fetch data"
                    }
                })
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = "Failed to fetch data"
            }
        }
    }
}