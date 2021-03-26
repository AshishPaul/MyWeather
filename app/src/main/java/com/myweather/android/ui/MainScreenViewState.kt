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
package com.myweather.android.ui

import com.myweather.android.data.local.db.CurrentWeatherData
import com.myweather.android.domain.Lce

data class MainScreenViewState(
    val currentWeatherData: CurrentWeatherData? = null,
    val loading: Boolean = false,
    val showError: Boolean = false,
    val errorText: String = ""
)

fun MainScreenViewState.resultToViewState(result: Lce<CurrentWeatherData>) =
    when (result) {
        is Lce.Loading -> this.copy(
            loading = true,
            showError = false
        )
        is Lce.Content -> {
            this.copy(
                loading = false,
                showError = false,
                currentWeatherData = result.data
            )
        }
        is Lce.Error -> {
            if (this.currentWeatherData == null) {
                this.copy(
                    loading = false,
                    showError = true
                )
            } else {
                this
            }
        }
    }