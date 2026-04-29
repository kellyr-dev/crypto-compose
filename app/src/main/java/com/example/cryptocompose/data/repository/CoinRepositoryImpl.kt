package com.example.cryptocompose.data.repository


import com.example.cryptocompose.data.db.CoinDao
import com.example.cryptocompose.data.mappers.Coin
import com.example.cryptocompose.data.mappers.toDomain
import com.example.cryptocompose.data.mappers.toEntity
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import com.example.cryptocompose.data.remote.CoinGeckoAPI
import com.example.cryptocompose.data.remote.CoinRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val remote: CoinRemoteDataSource,
    private val dao: CoinDao
) : CoinRepository {

    override fun observeCoins(): Flow<List<Coin>> {
        return dao.observeCoins()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun refreshCoins() {
        val remoteCoins = remote.getCoins()
        dao.upsertCoins(remoteCoins.map { it.toEntity() })
    }

    override suspend fun getCoinById(id: String): Result<CoinDetail> {
        return try {
            Result.success(remote.getCoinById(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}