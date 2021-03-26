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
package com.myweather.android.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import com.myweather.android.data.local.db.CurrentWeatherDao
import com.myweather.android.data.local.db.CurrentWeatherData
import com.myweather.android.data.remote.RemoteDataSource
import com.myweather.android.data.remote.model.WeatherApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentWeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val currentWeatherDao: CurrentWeatherDao
) {

    suspend fun syncCurrentWeatherData(params: Map<String, String>): DataResult<CurrentWeatherData> =
        withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getCurrentWeatherData(params)
                return@withContext if (response.isSuccessful) {
                    val body = response.body()
                    // Empty body
                    if (body == null || response.code() != 200) {
                        DataResult.Error(BaseError())
                    } else {
                        val data = CurrentWeatherData.createFromApiResponse(body)
                        currentWeatherDao.insert(data)
                        DataResult.Content(data)
                    }
                } else {
                    val msg = response.errorBody()?.string()
                    val errorMessage = if (msg.isNullOrEmpty()) {
                        response.message()
                    } else {
                        msg
                    }
                    DataResult.Error(
                        BaseError(
                            errorCode = response.code(),
                            throwable = Throwable(errorMessage)
                        )
                    )
                }
            } catch (t: Throwable) {
                return@withContext DataResult.Error(BaseError(throwable = t))
            }
        }

    fun getCurrentWeatherData(
        coroutineScope: CoroutineScope?
    ): LiveData<CurrentWeatherData> {
        val locationUniqueId: String = ""
        val params: Map<String, String> = mapOf()
        val localData = currentWeatherDao.getCurrentWeatherData(locationUniqueId)
        if (localData.value == null) {
            coroutineScope?.launch { syncCurrentWeatherData(getCurrentWeatherParams()) }
        }
        return localData

    }

    private fun getCurrentWeatherParams(): Map<String, String> {

        return mapOf()
    }

}