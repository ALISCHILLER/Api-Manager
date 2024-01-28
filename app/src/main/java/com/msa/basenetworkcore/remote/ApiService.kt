package com.msa.basenetworkcore.remote


import com.msa.basenetworkcore.remote.model.UserData
import com.msa.basenetworkcore.remote.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Replace with your actual API endpoints and request/response models
    @GET("users/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): Response<UserData>

    @GET("user-data")
    suspend fun fetchUserData(): Response<UserData?>

    @GET("weather")
    /** To get weather information based on city*/
    suspend fun  fetchWeather(@Query("q") cityName:String, @Query("appid") appId:String): Response<WeatherResponse?>
    @GET("weather")
    /** To get weather information based on city*/
    suspend fun fetchWeather(@Query("lat") lat:Double, @Query("lon") long:Double, @Query("appid") appId:String): Response<WeatherResponse>?
}