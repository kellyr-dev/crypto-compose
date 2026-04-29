package com.example.cryptocompose.di

import android.content.Context
import androidx.room.Room
import com.example.cryptocompose.common.Constants
import com.example.cryptocompose.data.db.CoinDao
import com.example.cryptocompose.data.db.CoinDatabase
import com.example.cryptocompose.data.remote.CoinGeckoAPI
import com.example.cryptocompose.data.remote.CoinRemoteDataSource
import com.example.cryptocompose.data.repository.CoinRepository
import com.example.cryptocompose.data.repository.CoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideGeckoAPI(client: OkHttpClient): CoinGeckoAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(CoinGeckoAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CoinDatabase {
        return Room.databaseBuilder(
            context,
            CoinDatabase::class.java,
            "crypto_db"
        ).build()
    }

    @Provides
    fun provideCoinDao(db: CoinDatabase): CoinDao {
        return db.CoinDao()
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        remote: CoinRemoteDataSource,
        dao: CoinDao
    ): CoinRepository {
        return CoinRepositoryImpl(remote, dao)
    }

}