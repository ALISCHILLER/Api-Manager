package com.msa.basenetworkcore.remote.networkManger


import com.msa.basenetworkcore.remote.model.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

class ApiManager(private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {

    private val _apiResultStateFlow = MutableStateFlow<ApiResult<Any?>>(ApiResult.Initial)
    val apiResultStateFlow: Flow<ApiResult<Any?>> = _apiResultStateFlow.asStateFlow()

    fun <T : Any> makeApiCall(apiCall: suspend() -> Response<T?>) {
        coroutineScope.launch {
            try {
                _apiResultStateFlow.emit(ApiResult.Loading)
                Timber.i("Loading ")

                val response = apiCall()
                Timber.i("response:" + response)
                handleResponse(response)
            } catch (e: Exception) {
                handleException(e)
                Timber.i("handleException:" + e)
            }
        }
    }

    private suspend fun <T : Any> handleResponse(response: Response<T?>) {
        if (response.isSuccessful) {
            val responseData = response.body()
            if (responseData != null) {
                _apiResultStateFlow.emit(ApiResult.Success(responseData))
                Timber.i("Success:" + responseData)
            } else {
                Timber.i("Error: Null response body")
                _apiResultStateFlow.emit(ApiResult.Error(ApiError.GenericError("Null response body")))
            }
        } else {
            handleError(response)
            Timber.i("handleError:" + response.message())
        }
    }

    private fun handleError(response: Response<*>) {
        _apiResultStateFlow.value = when (response.code()) {
            401 -> ApiResult.Error(ApiError.Unauthorized)
            403 -> ApiResult.Error(ApiError.Forbidden)
            else -> ApiResult.Error(ApiError.GenericError(response.message()))
        }
    }

    private fun handleException(e: Exception) {
        _apiResultStateFlow.value = ApiResult.Error(ApiError.HandleException(e.toString()))
    }
}


sealed class ApiResult<out T> {
    object Initial : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val error: ApiError) : ApiResult<Nothing>()
}

sealed class ApiError {
    object Unauthorized : ApiError()
    object Forbidden : ApiError()
    data class GenericError(val message: String) : ApiError()
    data class NetworkError(val message: String) : ApiError()
    data class HandleException(val message: String) : ApiError()
}



//suspend fun getWeatherData3(city: String): Flow<ApiResult<WeatherResponse>> = flow {
//    coroutineScope {
//        // You need to be in a coroutine scope to call a suspend function
//        apiManager.makeApiCall {
//            apiService.fetchWeather(city, appId)
//        }
//
//        // Now, you can collect the result within the same coroutine scope
//        apiManager.apiResultStateFlow.collect { result ->
//            // Handle the result
//            emit(result as ApiResult<WeatherResponse>)
//        }
//    }
//}
//
//suspend fun getUserData2(): Flow<ApiResult<Any?>> {
//    // Use flow to emit the loading state before making the API call
//    apiManager.makeApiCall { apiService.fetchUserData() }
//
//    return apiManager.apiResultStateFlow
////        apiManager.apiResultStateFlow.collect { result ->
////        // Handle the result
////        }
//}
