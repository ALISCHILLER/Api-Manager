package com.msa.basenetworkcore.remote

import com.msa.basenetworkcore.remote.model.UserData
import com.msa.basenetworkcore.remote.model.WeatherResponse
import com.msa.basenetworkcore.remote.networkManger.ApiManager
import com.msa.basenetworkcore.remote.networkManger.ApiResult
import com.msa.basenetworkcore.remote.networkManger.MakeSafeApiCall
import com.msa.basenetworkcore.remote.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val appId = "45e4e8b5f521a8bf54544c5a19f06c20"

class ApiRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiManager: MakeSafeApiCall,
) {
    suspend fun fetchWeather(): Flow<Resource<UserData?>> {
        return apiManager.makeSafeApiCall {
            withContext(Dispatchers.IO) {
                apiService.fetchUserData()
            }
        }
    }
    suspend fun fetchWeather1(): Flow<Resource<WeatherResponse?>> {
        return apiManager.makeSafeApiCall {
            withContext(Dispatchers.IO) {
                apiService.fetchWeather("tehran", appId)
            }
        }
    }

}