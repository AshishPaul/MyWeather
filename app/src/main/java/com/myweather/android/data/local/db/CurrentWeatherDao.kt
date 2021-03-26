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

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

const val QUERY_GET_CURRENT_WEATHER_DATA =
    "SELECT * FROM TABLE_CURRENT_WEATHER_DATA WHERE id = :locationUniqueId"

@Dao
interface CurrentWeatherDao {

    @Insert
    suspend fun insert(item: CurrentWeatherData)

    @Query(QUERY_GET_CURRENT_WEATHER_DATA)
    fun getCurrentWeatherData(locationUniqueId: String): LiveData<CurrentWeatherData>
}