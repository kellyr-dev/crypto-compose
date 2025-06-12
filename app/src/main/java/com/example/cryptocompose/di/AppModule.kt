package com.example.cryptocompose.di

import android.util.Log
import com.example.cryptocompose.common.Constants
import com.example.cryptocompose.data.remote.CoinGeckoAPI
import com.example.cryptocompose.data.repository.CoinRepository
import com.example.cryptocompose.data.repository.CoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(60 * 60, TimeUnit.SECONDS)
            .readTimeout(60 * 60, TimeUnit.SECONDS)
            .writeTimeout(60 * 60, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideGeckoAPI(): CoinGeckoAPI {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Log.d("AppModule", "retrofit instance created ${retrofit}")

        return retrofit.create(CoinGeckoAPI::class.java)
    }

}