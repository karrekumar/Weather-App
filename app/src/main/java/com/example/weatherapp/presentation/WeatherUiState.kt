package com.example.weatherapp.presentation
import com.example.weatherapp.domain.model.Weather

sealed interface WeatherUiState {
    data object Empty : WeatherUiState
    data object Loading : WeatherUiState
    data class Success(val weather: Weather) : WeatherUiState
    data class Error(val error: Throwable) : WeatherUiState
}