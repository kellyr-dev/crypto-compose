package com.example.cryptocompose.data.repository


import androidx.paging.PagingData
import com.example.cryptocompose.data.database.CoinDao
import com.example.cryptocompose.data.database.CoinDatabase
import com.example.cryptocompose.data.mappers.ChartRange
import com.example.cryptocompose.data.mappers.Coin
import com.example.cryptocompose.data.mappers.CoinChart
import com.example.cryptocompose.data.mappers.toDomain
import com.example.cryptocompose.data.mappers.toEntity
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.remote.CoinGeckoAPI
import com.example.cryptocompose.data.remote.CoinRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val remote: CoinRemoteDataSource,
    private val dao: CoinDao,
    private val api: CoinGeckoAPI,
    private val database: CoinDatabase
) : CoinRepository {

    override fun observeCoins(): Flow<List<Coin>> {
        return dao.observeCoins()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override fun searchCoins(query: String): Flow<List<Coin>> {
        return dao.searchCoins(query.trim())
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

    override suspend fun getCoinChart(coinId: String, range: ChartRange): Result<CoinChart> {
        return try {
            Result.success(
                remote.getCoinChart(coinId = coinId, days = range.days))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getPagedCoins(): Flow<PagingData<Coin>> {
        TODO("Not yet implemented")
    }


}