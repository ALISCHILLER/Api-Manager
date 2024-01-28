package com.msa.basenetworkcore.di

import com.msa.basenetworkcore.remote.networkManger.ApiManager
import com.msa.basenetworkcore.remote.networkManger.MakeSafeApiCall
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiManagerModule {

    @Singleton
    @Provides
    fun provideApiManager(): ApiManager {
        return ApiManager()
    }


}