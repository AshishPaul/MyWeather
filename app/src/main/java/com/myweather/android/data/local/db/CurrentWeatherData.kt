/*
 * Copyright (c) Ashish , 2021.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.myweather.android.data.local.db

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myweather.android.data.remote.model.WeatherApiResponse
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = "TABLE_CURRENT_WEATHER_DATA")
data class CurrentWeatherData(
    @PrimaryKey val id: String,
    val longitude: Double,
    val latitude: Double,
    val weather: String,
    val description: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val pressure: Double,
    val humidity: Double,
    val speed: String,
    val deg: String,
    val gust: String,
    val country: String,
    val sunrise: Long,
    val sunset: Long,
    val name: String,
) : Parcelable {

    companion object {

        fun createFromApiResponse(weatherApiResponse: WeatherApiResponse): CurrentWeatherData =
            CurrentWeatherData(
                weatherApiResponse.location.getLocationUniqueId(),
                weatherApiResponse.location.longitude,
                weatherApiResponse.location.latitude,
                if (weatherApiResponse.weather.isNotEmpty()) {
                    weatherApiResponse.weather[0].main
                } else {
                    ""
                },
                if (weatherApiResponse.weather.isNotEmpty()) {
                    weatherApiResponse.weather[0].description
                } else {
                    ""
                },
                weatherApiResponse.main.temperature,
                weatherApiResponse.main.feelsLike,
                weatherApiResponse.main.minTemperature,
                weatherApiResponse.main.maxTemperature,
                weatherApiResponse.main.pressure,
                weatherApiResponse.main.humidity,
                weatherApiResponse.wind.speed,
                weatherApiResponse.wind.deg,
                weatherApiResponse.wind.gust,
                weatherApiResponse.sys.country,
                weatherApiResponse.sys.sunrise,
                weatherApiResponse.sys.sunset,
                weatherApiResponse.name

            )
    }
}