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
    private val repository : CoinRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state  = _state.asStateFlow()

    init {

        viewModelScope.launch {
            val response = repository.getCoins()

            if (response.isSuccessful){

                val completeList = response.body()!!

                _state.value = _state.value.copy(
                    list = completeList,
                    topGainers = completeList.sortedByDescending { it.priceChangePercentage24h }.take(20),
                    topLosers = completeList.sortedBy { it.priceChangePercentage24h }.take(20)
                )
            } else {
                Log.e(TAG, "Error fetching: ${response.errorBody()}")
            }
        }
    }

}

data class CoinListState(

    val list : List<CoinList> = emptyList(),
    val topGainers : List<CoinList> = emptyList(),
    val topLosers : List<CoinList> = emptyList(),
    val page : Int = 1

)