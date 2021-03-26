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

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.myweather.android.BuildConfig
import com.myweather.android.data.remote.RemoteDataSource
import com.myweather.android.data.remote.retrofit.MyWeatherService
import com.myweather.android.data.remote.retrofit.NullOnEmptyConverterFactory
import com.myweather.android.data.remote.retrofit.RetrofitDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRemoteDataSource(weatherService: MyWeatherService): RemoteDataSource =
        RetrofitDataSource(weatherService)

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return Json.asConverterFactory(contentType)

//        return GsonConverterFactory
//            .create(
//                GsonBuilder()
//                .setLenient()
//                .disableHtmlEscaping()
//                .create())
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        converterFactory: Converter.Factory, client: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_API_BASE_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideMyWeatherServiceService(retrofit: Retrofit): MyWeatherService {
        return retrofit.create(MyWeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
//            .addInterceptor(MockInterceptor(BuildConfig.DEBUG, app.applicationContext.assets))
            .build()
    }
}