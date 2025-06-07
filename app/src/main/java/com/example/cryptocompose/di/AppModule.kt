package com.example.cryptocompose.di

import com.example.cryptocompose.common.Constants
import com.example.cryptocompose.data.remote.CoinGeckoAPI
import com.example.cryptocompose.data.repository.CoinRepository
import com.example.cryptocompose.data.repository.CoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGeckoAPI(): CoinGeckoAPI {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinGeckoAPI) : CoinRepository {
        return CoinRepositoryImpl(api)
    }

}