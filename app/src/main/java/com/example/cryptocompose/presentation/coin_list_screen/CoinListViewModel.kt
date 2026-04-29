package com.example.cryptocompose.presentation.coin_list_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import com.example.cryptocompose.data.remote.CoinGeckoAPI
import com.example.cryptocompose.data.repository.CoinRepository
import com.example.cryptocompose.data.repository.CoinRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private val TAG = "CoinListViewModel"

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository : CoinRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state  = _state.asStateFlow()

    init {
        observeCoins()
        refreshCoins()
    }

    private fun observeCoins() {
        viewModelScope.launch {
            repository.observeCoins().collect{ coins ->
                _state.update {
                    it.copy(
                        list = coins,
                        topGainers = coins
                            .filter { coin -> coin.priceChangePercentage24h != null }
                            .sortedByDescending { coins -> coins.priceChangePercentage24h }
                            .take(20),

                        topLosers = coins
                            .filter { coin -> coin.priceChangePercentage24h != null }
                            .sortedBy { coins -> coins.priceChangePercentage24h }
                            .take(20)

                    )
                }
            }
        }
    }


    fun refreshCoins(){
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, errorMessage = null)  }

            try {
                repository.refreshCoins()
            } catch (e: Exception) {
                _state.update {
                    it.copy(errorMessage = "Could not refresh market data")
                }
            } finally {
                _state.update { it.copy(isRefreshing = false) }
            }
        }
    }

}