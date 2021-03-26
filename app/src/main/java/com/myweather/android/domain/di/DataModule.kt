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
package com.myweather.android.domain.di

import android.app.Application
import androidx.room.Room
import com.myweather.android.data.local.SharedPrefs
import com.myweather.android.data.local.db.AppDataBase
import com.myweather.android.data.local.db.CurrentWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesSharedPrefsDataSource(app: Application): SharedPrefs =
        SharedPrefs(app.applicationContext)

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDataBase {
        return Room
            .databaseBuilder(app, AppDataBase::class.java, "my_weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(db: AppDataBase): CurrentWeatherDao {
        return db.currentWeatherDao()
    }

}