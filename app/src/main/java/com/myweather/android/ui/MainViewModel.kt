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

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myweather.android.data.local.db.CurrentWeatherData
import com.myweather.android.data.repository.CurrentWeatherRepository
import com.myweather.android.domain.Lce
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val currentWeatherRepository: CurrentWeatherRepository) :
    ViewModel() {

    private val viewStateMLD = MutableLiveData<MainScreenViewState>()
    private var currentViewState = MainScreenViewState()
        set(value) {
            field = value
            viewStateMLD.value = value
        }

    val viewState = MediatorLiveData<MainScreenViewState>().apply {
        addSource(
            currentWeatherRepository.getCurrentWeatherData(viewModelScope)
        ) {
            it?.let { currentViewState = currentViewState.resultToViewState(Lce.Content(it)) }
        }

        addSource(viewStateMLD) {
            value = it
        }
    }


}