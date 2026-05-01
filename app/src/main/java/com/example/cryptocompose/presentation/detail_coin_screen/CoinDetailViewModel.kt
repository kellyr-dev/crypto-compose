package com.example.cryptocompose.presentation.detail_coin_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocompose.data.mappers.ChartRange
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.repository.CoinRepository
import com.example.cryptocompose.data.repository.CoinRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CoinDetailViewModel"

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()


    fun loadCoin(id: String) {
        getCoin(id)
        getChart(id, _state.value.selectedRange)
    }

    private fun getCoin(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoadingCoin = true, errorMessage = null)
            }

            val response = repository.getCoinById(id)

            if (response.isSuccess) {
                _state.update {
                    it.copy(coin = response.getOrNull(), isLoadingCoin = false)
                }
            } else {
                _state.update {
                    it.copy(
                        errorMessage = response.exceptionOrNull()?.message,
                        isLoadingCoin = false
                    )
                }

            }
        }
    }

    private fun getChart(id: String, range: ChartRange) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoadingChart = true, errorMessage = null)
            }

            val response = repository.getCoinChart(id, range)

            if (response.isSuccess) {
                _state.update {
                    it.copy(chart = response.getOrNull(), isLoadingChart = false)
                }
            } else {
                _state.update {
                    it.copy(
                        errorMessage = response.exceptionOrNull()?.message,
                        isLoadingChart = false
                    )
                }
            }

        }
    }

    fun onChartRangeSelected(id: String, range: ChartRange) {
        _state.update {
            it.copy(selectedRange = range)
        }

        getChart(id, range)
    }
}
