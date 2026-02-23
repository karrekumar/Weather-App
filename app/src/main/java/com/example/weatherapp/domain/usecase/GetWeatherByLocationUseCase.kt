package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * Use Case implementation for retrieving weather information using device location coordinates.
 * This use case is typically triggered when the user grants location permissions and requests
 * weather for their current location.
 *
 * @property repository The [WeatherRepository] used to fetch weather data.
 */
class GetWeatherByLocationUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    /**
     * Executes the use case to fetch weather for specific coordinates.
     *
     * This function delegates the call to the repository and wraps the response in a [Result].
     * It handles potential exceptions from the data layer gracefully.
     *
     * @param latitude The latitude coordinate.
     * @param longitude The longitude coordinate.
     * @return A [Result] containing the [Weather] object if successful, or the exception if failed.
     */
    suspend operator fun invoke(latitude: Double, longitude: Double): Result<Weather> {
        return try {
            // Fetch weather based on latitude and longitude
            val weather = repository.getWeatherByLocation(latitude, longitude)
            Result.success(weather)
        } catch (e: Exception) {
            // Gracefully handle location or network related failures
            Result.failure(e)
        }
    }
}