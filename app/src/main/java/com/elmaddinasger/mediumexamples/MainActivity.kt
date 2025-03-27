package com.elmaddinasger.mediumexamples

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elmaddinasger.mediumexamples.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val city = binding.inpedtCity.text.toString()
            getWeather(city)

        }
    }

    private fun getWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = WeatherRepository.weatherApiService.getWeather(city)
            if (response.isSuccessful) {
                val weatherResponse = response.body()
                weatherResponse?.let {
                    val weatherDescription = it.weather[0].description
                    val temperature = it.main.temp
                    val humidity = it.main.humidity
                    val cityName = it.name
                    withContext(Dispatchers.Main){
                        val weatherInfo = "Şəhər: $cityName\n" +
                                "Hava: $weatherDescription\n" +
                                "Temperatur: $temperature°C\n" +
                                "Rütubət: $humidity%"

                        binding.textView.text = weatherInfo
                    }
                }
            }
        }
    }
}