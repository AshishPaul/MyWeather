/*
 * Copyright (c) Ashish , 2020.
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

package com.myweather.android.data.remote.retrofit

import com.myweather.android.data.remote.model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.*

interface MyWeatherService {

    @POST("data/2.5/weather")
    suspend fun getCurrentWeatherData(@QueryMap params: Map<String, String>): Response<WeatherApiResponse>

}