package com.msa.basenetworkcore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msa.basenetworkcore.remote.ApiRepository
import com.msa.basenetworkcore.remote.model.UserData
import com.msa.basenetworkcore.remote.networkManger.ApiResult
import com.msa.basenetworkcore.remote.model.WeatherResponse
import com.msa.basenetworkcore.remote.util.MsaError
import com.msa.basenetworkcore.remote.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject



data class HomeScreenState(val isLoading:Boolean = false,
                           val weatherResponse: WeatherResponse?=null,
                           val userData: UserData?=null,
                           val error: MsaError?=null,
                           val currentCity:String? = null, val isForegroundServiceStarted:Boolean = false)
@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {
    private val _homeState:MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val homeState:StateFlow<HomeScreenState> = _homeState

    fun fetchWeather1() {
        // Observe the result from the repository
        viewModelScope.launch {
            apiRepository.fetchWeather1().onEach { response ->
                Timber.d(response.data.toString())
                when (response.status) {
                    Resource.Status.SUCCESS-> {
                        Log.d("HomeScreenViewModel", "fetchWeather1 SUCCESS:${response.data} ")
                        _homeState.update { it.copy(isLoading = false, weatherResponse = response.data) }
                    }
                    Resource.Status.LOADING-> {
                        Log.d("HomeScreenViewModel" , "fetchWeather1 LOADING:${response.status} ")
                        _homeState.update { it.copy(isLoading = true) }
                    }
                    Resource.Status.ERROR -> {
                        _homeState.update { it.copy(isLoading = false, error = response.error) }
                        Log.d("HomeScreenViewModel" , "fetchWeather1 ERROR:${response.error} ")

                    }
                    else -> {
                        Log.d("HomeScreenViewModel" , "fetchWeather1 else:$response ")
                    }
                }
            }.collect()
        }
    }

    fun fetchUserData() {
        // Observe the result from the repository
        viewModelScope.launch  {
            apiRepository.fetchWeather().onEach { response ->
                Timber.d(response.data.toString())
                when (response.status) {
                    Resource.Status.SUCCESS-> {
                        Log.d("HomeScreenViewModel", "fetchUserData SUCCESS:${response.data} ")
                        _homeState.update { it.copy(isLoading = false, userData = response.data) }
                    }
                    Resource.Status.LOADING-> {
                        Log.d("HomeScreenViewModel", "fetchUserData LOADING:${response.status} ")
                        _homeState.update { it.copy(isLoading = true) }
                    }
                    Resource.Status.ERROR -> {
                        Log.d("HomeScreenViewModel", "fetchUserData ERROR:${response.error} ")
                        _homeState.update { it.copy(isLoading = false, error = response.error) }
                    }
                    else -> {
                        Log.d("HomeScreenViewModel", "fetchUserData else:$response ")
                    }
                }
            }.collect()
        }
    }


}