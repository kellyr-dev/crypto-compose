package com.example.cryptocompose.presentation.detail_coin_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocompose.data.model.gecko.CoinDetail
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
    private val repository : CoinRepositoryImpl
): ViewModel() {

    private val _state = MutableStateFlow<CoinDetail?>(null)
    val state = _state.asStateFlow()


    fun getCoin(id: String){
        viewModelScope.launch {

            val response = repository.getCoinById(id)
            if (response.isSuccess){
                _state.update { response.getOrNull() }
            } else {
                Log.e(TAG, "Error fetching in CoinDetailViewModel: ${response.exceptionOrNull()?.message}")
            }

        }
    }


}
