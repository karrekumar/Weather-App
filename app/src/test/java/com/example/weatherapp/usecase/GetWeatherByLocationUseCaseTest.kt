package com.example.weatherapp.usecase

import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [GetWeatherByLocationUseCase].
 * Ensures that the use case correctly interacts with the repository and handles success/failure states.
 *
 * Note: This file was previously containing WeatherViewModelTest content. It has been corrected to test the UseCase.
 */
class GetWeatherByLocationUseCaseTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var useCase: GetWeatherByLocationUseCase

    @Before
    fun setup() {
        useCase = GetWeatherByLocationUseCase(repository)
    }

    /**
     * Verifies that the use case returns a success Result containing the weather data
     * when the repository successfully fetches data by location.
     */
    @Test
    fun `returns success when repository returns weather`() = runTest {
        // Given
        val weather = Weather("Krugerville", 8.7, "clear sky", "icon")
        val lat = 33.0
        val lon = -96.0
        coEvery { repository.getWeatherByLocation(lat, lon) } returns weather

        // When
        val result = useCase(lat, lon)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(weather, result.getOrNull())
    }

    /**
     * Verifies that the use case returns a failure Result containing the exception
     * when the repository throws an exception (e.g. network error).
     */
    @Test
    fun `returns failure when repository throws exception`() = runTest {
        // Given
        val exception = RuntimeException("API error")
        val lat = 33.0
        val lon = -96.0
        coEvery { repository.getWeatherByLocation(any(), any()) } throws exception

        // When
        val result = useCase(lat, lon)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
