package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * Use Case implementation for retrieving weather information based on a city name.
 * This class encapsulates the business logic for fetching weather data by city,
 * decoupling the ViewModel from the Repository implementation details.
 *
 * @property repository The [WeatherRepository] used to fetch weather data.
 */
class GetWeatherByCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    /**
     * Executes the use case to fetch weather for a specific city.
     *
     * This function delegates the call to the repository and wraps the response in a [Result].
     * Any exceptions thrown by the repository (e.g., network errors, parsing errors) are caught
     * and returned as a [Result.failure].
     *
     * @param city The name of the city to fetch weather for.
     * @return A [Result] containing the [Weather] object if successful, or the exception if failed.
     */
    suspend operator fun invoke(city: String): Result<Weather> {
        return try {
            // Delegate data fetching to repository
            val weather = repository.getWeatherByCity(city)
            Result.success(weather)
        } catch (e: Exception) {
            // Defensive handling to avoid crashing the UI on API/network errors
            Result.failure(e)
        }
    }
}