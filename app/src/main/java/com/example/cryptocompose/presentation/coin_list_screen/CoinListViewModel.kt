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

    private val _state = MutableStateFlow<List<CoinList>>(emptyList())
    val state  = _state.asStateFlow()

    init {

        viewModelScope.launch {
            val response = repository.getCoins()

            if (response.isSuccessful){
                _state.update { response.body()!! }
            } else {
                Log.e(TAG, "Error fetching: ${response.errorBody()}")
            }
        }
    }

}

data class CoinListState(

    val isLoading : Boolean = false,
    val coins : List<CoinList> = emptyList(),
    val error : String = ""

)