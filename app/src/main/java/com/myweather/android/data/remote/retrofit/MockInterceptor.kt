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

import android.content.res.AssetManager
import kotlinx.io.InputStream
import kotlinx.serialization.InternalSerializationApi
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber


@InternalSerializationApi
class MockInterceptor(private val debug: Boolean, private val assets: AssetManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val path = chain.request().url.toUri().path
        Timber.tag(TAG).d("intercept: path=$path")

        val response = interceptRequestWhenDebug(chain, createMockRequestTypeFromPath(path))

        return response ?: chain.proceed(chain.request())
    }

    private fun interceptRequestWhenDebug(
        chain: Interceptor.Chain,
        requestType: MockRequestType?
    ): Response? {
        return if (debug) {
            val request = chain.request()
            val dataJson = getDataJsonFromRequestType(requestType)
            getHttpSuccessResponse(request, dataJson)
        } else {
            null
        }
    }

    private fun createMockRequestTypeFromPath(path: String): MockRequestType =
        when {
            path.endsWith("/api/clans", ignoreCase = true) -> {
                MockRequestType.GetClans
            }
            path.contains("/api/clans/", ignoreCase = true) -> {
                val pathWithId = path.split("/api/clans/")
                MockRequestType.GetClansById(pathWithId[1])
            }
            else -> MockRequestType.GetClans
        }

    private fun getDataJsonFromRequestType(requestType: MockRequestType?): String? =
        requestType?.let {
            assets.getStringFromFile(it.getJsonPath())
        }

    private fun getHttpSuccessResponse(request: Request, dataJson: String?): Response {
        val response: Response
        if (dataJson.isNullOrBlank()) {
            Timber.tag(TAG).d("getHttpSuccessResponse: dataJson is empty!")
            response = Response.Builder()
                .code(500)
                .protocol(Protocol.HTTP_1_0)
                .request(request)
                //protocol&request be set,otherwise will be exception.
                .build()
        } else {
            response = Response.Builder()
                .code(200)
                .message(dataJson)
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("Success-Type", "application/json")
                .body(dataJson.toResponseBody("application/json".toMediaTypeOrNull()))
                .build()
        }
        return response
    }

    companion object {
        private const val TAG = "MockInterceptor"
        const val GET_CLANS_JSON = "get_clans"
    }

    sealed class MockRequestType(val filePath: String) {
        object GetClans : MockRequestType(GET_CLANS_JSON)
        data class GetClansById(val id: String) : MockRequestType("${GET_CLANS_JSON}_$id")
    }

    private fun MockRequestType.getJsonPath(): String = this.filePath + ".json"

    @InternalSerializationApi
    private fun AssetManager.getStringFromFile(filePath: String): String = try {
        val inputStream: InputStream = this.open(filePath)
        val inputString = inputStream.bufferedReader().use { it.readText() }
        inputString
    } catch (e: Exception) {
//        Timber.tag("AssetManager.getStringFromFile: $filePath").d(e.toString())
        ""
    }
}