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
package com.myweather.android.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherApiResponse(
    @SerialName("coord") val location: Location,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("main") val main: Main,
    @SerialName("wind") val wind: Wind,
    @SerialName("sys") val sys: Sys,
    @SerialName("name") val name: String,
    @SerialName("cod") val cod: String,

    )

@Serializable
data class Location(
    @SerialName("lat") val longitude: Double,
    @SerialName("lon") val latitude: Double

) {
    fun getLocationUniqueId(): String = "$longitude$latitude"
}

@Serializable
data class Weather(
    @SerialName("id") val id: String,
    @SerialName("description") val description: String,
    @SerialName("main") val main: String,
)

@Serializable
data class Main(
    @SerialName("temp") val temperature: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("temp_min") val minTemperature: Double,
    @SerialName("temp_max") val maxTemperature: Double,
    @SerialName("pressure") val pressure: Double,
    @SerialName("humidity") val humidity: Double,
)

@Serializable
data class Wind(
    @SerialName("speed") val speed: String,
    @SerialName("deg") val deg: String,
    @SerialName("gust") val gust: String,
)

@Serializable
data class Sys(
    @SerialName("id") val id: String,
    @SerialName("type") val type: String,
    @SerialName("country") val country: String,
    @SerialName("sunrise") val sunrise: Long,
    @SerialName("sunset") val sunset: Long
)